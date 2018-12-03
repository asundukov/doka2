package io.cutebot.doka2.service;

import org.junit.Test;

import static io.cutebot.doka2.service.LevelCalc.getLevelByExp;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class LevelCalcTest {

    @Test
    public void getLevel1Zero() {
        assertThat(getLevelByExp(0), equalTo(1));
    }

    @Test
    public void getLevel1() {
        assertThat(getLevelByExp(50), equalTo(1));
    }

    @Test
    public void getLevel2Step() {
        assertThat(getLevelByExp(100), equalTo(2));
    }

    @Test
    public void getLevel3() {
        assertThat(getLevelByExp(200), equalTo(3));
    }

    @Test
    public void getLevel3End() {
        assertThat(getLevelByExp(349), equalTo(3));
    }

    @Test
    public void getLevel4() {
        assertThat(getLevelByExp(350), equalTo(4));
    }

}
