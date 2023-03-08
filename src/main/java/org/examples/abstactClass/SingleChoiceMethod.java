package org.examples.abstactClass;

import java.util.Random;

public class SingleChoiceMethod extends AbstractQuest{
    @Override
    public boolean check(Answer asnswer) {
        return new Random().nextBoolean();
    }
}
