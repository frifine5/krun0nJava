package com.cyk.dsp.t.ch2;


import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

/**
 * 外观模式
 * <p>
 * 外观模式的定义与特点
 * 外观（Facade）模式的定义：是一种通过为多个复杂的子系统提供一个一致的接口，而使这些子系统更加容易被访问的模式。该模式对外有一个统一接口，外部应用程序不用关心内部子系统的具体的细节，这样会大大降低应用程序的复杂度，提高了程序的可维护性。
 * <p>
 * 外观（Facade）模式是“迪米特法则”的典型应用，它有以下主要优点。
 * <p>
 * 降低了子系统与客户端之间的耦合度，使得子系统的变化不会影响调用它的客户类。
 * 对客户屏蔽了子系统组件，减少了客户处理的对象数目，并使得子系统使用起来更加容易。
 * 降低了大型软件系统中的编译依赖性，简化了系统在不同平台之间的移植过程，因为编译一个子系统不会影响其他的子系统，也不会影响外观对象。
 * <p>
 * <p>
 * 外观（Facade）模式的主要缺点如下。
 * <p>
 * 不能很好地限制客户使用子系统类。
 * 增加新的子系统可能需要修改外观类或客户端的源代码，违背了“开闭原则”。
 * <p>
 * 外观模式的结构与实现
 * 外观（Facade）模式的结构比较简单，主要是定义了一个高层接口。它包含了对各个子系统的引用，客户端可以通过它访问各个子系统的功能。现在来分析其基本结构和实现方法。
 * 1. 模式的结构
 * 外观（Facade）模式包含以下主要角色。
 * <p>
 * 外观（Facade）角色：为多个子系统对外提供一个共同的接口。
 * 子系统（Sub System）角色：实现系统的部分功能，客户可以通过外观角色访问它。
 * 客户（Client）角色：通过一个外观角色访问各个子系统的功能。
 *
 * @author WangChengyu
 * 2019/6/28 10:16
 */
public class TestFacade1 {
    // 各个子系统
    // 外观角色


    public static void main(String[] args) {
        JFrame f = new JFrame("外观模式: 婺源特产选择测试");
        Container cp = f.getContentPane();
        WySpecialty2 wys = new WySpecialty2();
        JScrollPane treeView = new JScrollPane(wys.tree);
        JScrollPane scrollpane = new JScrollPane(wys.label);
        JSplitPane splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, treeView, scrollpane); //分割面版
        splitpane.setDividerLocation(230);     //设置splitpane的分隔线位置
        splitpane.setOneTouchExpandable(true); //设置splitpane可以展开或收起
        cp.add(splitpane);
        f.setSize(700, 500);
        f.setLocation(200, 200);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}



class WySpecialty2 extends JPanel implements TreeSelectionListener {
    private static final long serialVersionUID = 1L;
    final JTree tree;
    JLabel label;
    private Specialty1 s1 = new Specialty1();
    private Specialty2 s2 = new Specialty2();
    private Specialty3 s3 = new Specialty3();
    private Specialty4 s4 = new Specialty4();
    private Specialty5 s5 = new Specialty5();
    private Specialty6 s6 = new Specialty6();
    private Specialty7 s7 = new Specialty7();
    private Specialty8 s8 = new Specialty8();

    WySpecialty2() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("婺源特产");
        DefaultMutableTreeNode node1 = null, node2 = null, tempNode = null;
        node1 = new DefaultMutableTreeNode("婺源四大特产（红、绿、黑、白）");
        tempNode = new DefaultMutableTreeNode("婺源荷包红鲤鱼");
        node1.add(tempNode);
        tempNode = new DefaultMutableTreeNode("婺源绿茶");
        node1.add(tempNode);
        tempNode = new DefaultMutableTreeNode("婺源龙尾砚");
        node1.add(tempNode);
        tempNode = new DefaultMutableTreeNode("婺源江湾雪梨");
        node1.add(tempNode);
        top.add(node1);
        node2 = new DefaultMutableTreeNode("婺源其它土特产");
        tempNode = new DefaultMutableTreeNode("婺源酒糟鱼");
        node2.add(tempNode);
        tempNode = new DefaultMutableTreeNode("婺源糟米子糕");
        node2.add(tempNode);
        tempNode = new DefaultMutableTreeNode("婺源清明果");
        node2.add(tempNode);
        tempNode = new DefaultMutableTreeNode("婺源油煎灯");
        node2.add(tempNode);
        top.add(node2);
        tree = new JTree(top);
        tree.addTreeSelectionListener(this);
        label = new JLabel();
    }

    public void valueChanged(TreeSelectionEvent e) {
        if (e.getSource() == tree) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node == null) return;
            if (node.isLeaf()) {
                Object object = node.getUserObject();
                String sele = object.toString();
                label.setText(sele);
                label.setHorizontalTextPosition(JLabel.CENTER);
                label.setVerticalTextPosition(JLabel.BOTTOM);
                sele = sele.substring(2, 4);
                if (sele.equalsIgnoreCase("荷包")) label.setIcon(s1);
                else if (sele.equalsIgnoreCase("绿茶")) label.setIcon(s2);
                else if (sele.equalsIgnoreCase("龙尾")) label.setIcon(s3);
                else if (sele.equalsIgnoreCase("江湾")) label.setIcon(s4);
                else if (sele.equalsIgnoreCase("酒糟")) label.setIcon(s5);
                else if (sele.equalsIgnoreCase("糟米")) label.setIcon(s6);
                else if (sele.equalsIgnoreCase("清明")) label.setIcon(s7);
                else if (sele.equalsIgnoreCase("油煎")) label.setIcon(s8);
                label.setHorizontalAlignment(JLabel.CENTER);
            }
        }
    }

}

class Specialty1 extends ImageIcon {
    private static final long serialVersionUID = 1L;

    Specialty1() {
        super("D:\\0nRun\\springmvc\\pe-service\\src\\main\\resources\\pic\\WyImage\\Specialty11.jpg");
    }
}

class Specialty2 extends ImageIcon {
    private static final long serialVersionUID = 1L;

    Specialty2() {
        super("D:\\0nRun\\springmvc\\pe-service\\src\\main\\resources\\pic\\WyImage\\Specialty12.jpg");
    }
}

class Specialty3 extends ImageIcon {
    private static final long serialVersionUID = 1L;

    Specialty3() {
        super("D:\\0nRun\\springmvc\\pe-service\\src\\main\\resources\\pic\\WyImage\\Specialty13.jpg");
    }
}

class Specialty4 extends ImageIcon {
    private static final long serialVersionUID = 1L;

    Specialty4() {
        super("D:\\0nRun\\springmvc\\pe-service\\src\\main\\resources\\pic\\WyImage\\Specialty14.jpg");
    }
}

class Specialty5 extends ImageIcon {
    private static final long serialVersionUID = 1L;

    Specialty5() {
        super("D:\\0nRun\\springmvc\\pe-service\\src\\main\\resources\\pic\\WyImage\\Specialty21.jpg");
    }
}

class Specialty6 extends ImageIcon {
    private static final long serialVersionUID = 1L;

    Specialty6() {
        super("D:\\0nRun\\springmvc\\pe-service\\src\\main\\resources\\pic\\WyImage\\Specialty22.jpg");
    }
}

class Specialty7 extends ImageIcon {
    private static final long serialVersionUID = 1L;

    Specialty7() {
        super("D:\\0nRun\\springmvc\\pe-service\\src\\main\\resources\\pic\\WyImage\\Specialty23.jpg");
    }
}

class Specialty8 extends ImageIcon {
    private static final long serialVersionUID = 1L;

    Specialty8() {
        super("D:\\0nRun\\springmvc\\pe-service\\src\\main\\resources\\pic\\WyImage\\Specialty24.jpg");
    }
}
