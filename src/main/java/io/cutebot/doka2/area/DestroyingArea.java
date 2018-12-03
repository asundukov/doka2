package io.cutebot.doka2.area;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.model.Build;
import io.cutebot.doka2.service.build.DestroyInProgressService;
import io.cutebot.telegram.model.SendMessage;
import io.cutebot.telegram.model.SendMessageItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class DestroyingArea implements Area {
    private static final Logger log = LoggerFactory.getLogger(DestroyingArea.class);

    @Inject
    private MainArea mainArea;

    @Inject
    private DestroyInProgressService destroyInProgressService;

    @Override
    public SendMessage handleInput(Hero hero, String input) {
        Build build = destroyInProgressService.getByHero(hero);
        if (build != null) {
            SendMessageItem item = new SendMessageItem(hero);
            item.setText("Вы еще не закончили!\n" +
                    "Планируемое завершение через: " + build.getDestroyTimeRemaining() + "\n\n" +
                    "А пока можно расслабиться, попить чайку и пофлудить в @doka2_chat, " +
                    "посмотреть статистику города /world, героя /hero или почитать что вообще " +
                    "тут происходит /help");
            return SendMessage.items(item);
        }
        return SendMessage.redirect(mainArea);
    }

    @Override
    public SendMessage heroIsComing(Hero hero, Object data) {
        if (!(data instanceof Build)) {
            log.warn("Wrong destroying state {} coming with {}", hero, data);
            return SendMessage.redirect(mainArea);
        }
        Build build = (Build) data;
        SendMessageItem item = new SendMessageItem(hero);
        if (build.getHero().equals(hero)) {
            item.setText(destructSelfBuild());
        } else if (build.getHero().getRace().equals(hero.getRace())) {
            item.setText(destructFriendBuild(build));
        } else {
            item.setText(destructEnemyBuild(build));
        }

        return SendMessage.items(item);
    }

    private String destructEnemyBuild(Build build) {
        return "Вы нашли постройку игрока " + build.getHero().getFullHeroName() + " размером " + build.getSize() +
                ". Хорошо что она не кого-нибудь " +
                "из ваших. Это даст больше опыта. Уничтожаем...";
    }

    private String destructFriendBuild(Build build) {
        return "Вы нашли постройку игрока " + build.getHero().getFullHeroName() + " размером " + build.getSize() +
                ". И стали ее уничтожать \uD83D\uDE08";
    }

    private String destructSelfBuild() {
        return "Вы начали разрушать свою постройку. Чудесно, за это будет больше всего опыта!";
    }
}
