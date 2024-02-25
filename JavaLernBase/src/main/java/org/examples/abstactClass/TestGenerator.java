package org.examples.abstactClass;

import java.util.Random;

public class TestGenerator {
    public AbstractQuest[] generateTest(final int NUMBER_QUEST, int maxMode){
        AbstractQuest[] test = new AbstractQuest[NUMBER_QUEST];
        for(int i = 0; i < test.length; i++){
            int mode = new Random().nextInt(maxMode);
            test[i] = QuestFactory.getQuestFromFactory(mode);
        }
        return test;
    }
}
