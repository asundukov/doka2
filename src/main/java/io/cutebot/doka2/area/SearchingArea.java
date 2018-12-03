package io.cutebot.doka2.area;

import io.cutebot.doka2.Hero;
import io.cutebot.doka2.model.Search;
import io.cutebot.doka2.model.SearchType;
import io.cutebot.doka2.service.search.SearchInProgressService;
import io.cutebot.telegram.model.SendMessage;
import io.cutebot.telegram.model.SendMessageItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SearchingArea implements Area {

    private static final Logger log = LoggerFactory.getLogger(SearchingArea.class);

    @Inject
    private MainArea mainArea;

    @Inject
    private SearchInProgressService searchInProgressService;

    @Override
    public SendMessage handleInput(Hero hero, String input) {
        Search search = searchInProgressService.getByHero(hero);
        if (search != null) {
            SendMessageItem item = new SendMessageItem(hero);
            item.setText("Вы еще копошитесь!\n" +
                    "Закончите примерно через: " + search.getTimeRemaining() + "\n\n" +
                    "А пока можно расслабиться, попить чайку и пофлудить в @doka2_chat, " +
                    "посмотреть статистику города /world, героя /hero или почитать что вообще " +
                    "тут происходит /help");
            return SendMessage.items(item);
        }
        return SendMessage.redirect(mainArea);
    }

    @Override
    public SendMessage heroIsComing(Hero hero, Object data) {
        if (!(data instanceof SearchType)) {
            log.warn("Wrong call searching area with data {}", data);
            return SendMessage.redirect(mainArea);
        }
        SearchType searchType = (SearchType) data;

        searchInProgressService.createNewSearch(hero, searchType);

        SendMessageItem item = new SendMessageItem(hero);
        item.setText("Вы начали искать.");
        return SendMessage.items(item);
    }
}
