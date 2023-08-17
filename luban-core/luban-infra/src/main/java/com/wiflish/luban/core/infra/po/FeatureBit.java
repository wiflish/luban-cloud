package com.wiflish.luban.core.infra.po;

import java.io.Serial;
import java.io.Serializable;

/**
 * 扩展字段，用于标记数据状态值，位操作。可以定义一个或多个位表示一个或多种状态.
 * 
 * @author wiflish
 * @since  2012-6-7 下午11:41:05
 */
public class FeatureBit implements Serializable {
    @Serial
    private static final long serialVersionUID = -7411993598092794651L;
    private long value;

    public FeatureBit() {
    }

    public FeatureBit(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    /**
     * 检查featureBit是否在参数对应的字节全部已设置为1.
     * 
     * @param flagBit 待检查的标记值.
     * @return 当且仅当参数值对应的位都已设置为1返回true, 否则返回false.
     */
    public boolean getBits(long flagBit) {
        if (flagBit == 0) {
            return false;
        }
        return ((value & flagBit) == flagBit);
    }

    /**
     * 将flagBit对应的位设置到featureBit中。
     * 
     * @param flagBit 位标记对应的值.
     * @return 返回设置后的值.
     */
    public FeatureBit setBits(long flagBit) {
        value |= flagBit;
        return this;
    }
}
