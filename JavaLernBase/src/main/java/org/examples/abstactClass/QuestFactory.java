package org.examples.abstactClass;

public class QuestFactory {
    public static AbstractQuest getQuestFromFactory(int mode){
        switch (mode){
            case 0:
                return new DragAndDropQuest();
            case 1:
                return new SingleChoiceMethod();
            default:
                throw new IllegalArgumentException("illegalMode");
        }
    }
}
