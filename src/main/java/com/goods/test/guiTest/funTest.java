package com.goods.test.guiTest;

import com.goods.comm.po.Graph;
import com.goods.comm.po.MapLayout;
import com.goods.gui.fun;
import org.junit.jupiter.api.Test;

public class funTest {
    @Test
    void funtest(){
        fun fun = new fun();
        MapLayout mapLayout = new MapLayout();
        Graph graph = mapLayout.getGraph();
        graph.printGraph();
    }
}
