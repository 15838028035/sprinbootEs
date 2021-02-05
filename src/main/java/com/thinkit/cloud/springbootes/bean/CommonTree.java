package com.thinkit.cloud.springbootes.bean;

import com.zhongkexinli.micro.serv.common.vo.TreeNode;

public class CommonTree extends TreeNode {

    private String title;

    private String label;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
