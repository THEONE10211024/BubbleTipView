package pers.medusa.xiayong.library;

import pers.medusa.xiayong.library.drawable.BubbleDrawable;

/**
 * Created by xiayong on 3/2/16.
 */
public interface IBubbleView {
    BubbleDrawable.ArrowLocation getArrowDirection();
    float getArrowRelativePosition();
}
