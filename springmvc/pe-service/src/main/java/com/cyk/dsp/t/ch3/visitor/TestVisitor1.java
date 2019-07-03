package com.cyk.dsp.t.ch3.visitor;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class TestVisitor1 {
    public static void main(String[] args) {
        new MaterialWin();
    }


}


// 定义材料接口
interface Material {
    String accept(Company visitor);
}

//具体元素：纸
class Paper implements Material {
    public String accept(Company visitor) {
        return (visitor.create(this));
    }
}

//具体元素：铜
class Cuprum implements Material {
    public String accept(Company visitor) {
        return (visitor.create(this));
    }
}


// 定义公司接口
interface Company {
    String create(Cuprum cuprum);

    String create(Paper paper);
}

// 具体访问者：艺术公司
class ArtCompany implements Company{

    public String create(Paper paper){
        return "讲学图";
    }

    public String create(Cuprum cuprum){
        return "朱熹铜像";
    }
}

// 具体访问者：造币公司
class MintCompany implements Company{

    @Override
    public String create(Cuprum cuprum) {
        return "纸币";
    }

    @Override
    public String create(Paper paper) {
        return "铜币";
    }
}

// 对象结构角色： 材料类
class SetMaterial {
    private List<Material> list = new ArrayList<>();
    public String accept(Company visitor){
        Iterator<Material> i = list.iterator();
        String tmp = "";
        while(i.hasNext()){

            tmp += ((Material)i.next()).accept(visitor) + " ";

        }
        return tmp;
    }

    public void add(Material element){
        list.add(element);
    }

    public void remove(Material element){
        list.remove(element);
    }


}


class MaterialWin extends JFrame implements ItemListener {

    private static final long serialVersionUID = 1l;
    JPanel centerJp;
    SetMaterial os;     // 材料集对象
    Company visitor1, visitor2;
    String[] select;

    MaterialWin() {
        super("访问者模式： 艺术公司和造币公司");
        JRadioButton art;
        JRadioButton mint;
        os = new SetMaterial();
        os.add(new Cuprum());
        os.add(new Paper());
        visitor1 = new ArtCompany();
        visitor2 = new MintCompany();
        this.setBounds(10, 10, 750, 350);
        this.setLocation(200, 200);
        this.setResizable(false);

        centerJp = new JPanel();
        this.add("Center", centerJp);
        JPanel southJP = new JPanel();
        JLabel jLabel01 = new JLabel("原材料有纸和铜，请选择生产公司：");
        art = new JRadioButton("艺术公司", true);
        mint = new JRadioButton("造币公司");
        art.addItemListener(this);
        mint.addItemListener(this);

        ButtonGroup group = new ButtonGroup();
        group.add(art);
        group.add(mint);
        southJP.add(jLabel01);
        southJP.add(art);
        southJP.add(mint);
        this.add("South", southJP);
        // 获取产品名
        select = (os.accept(visitor1)).split(" ");
        showPicture(select[0], select[1]);

    }


    void showPicture(String cuprum, String paper){
        System.out.println(String.format("cuprum = %s, paper = %s", cuprum, paper));
        centerJp.removeAll();   // 清除面板内容
        centerJp.repaint();     // 刷新屏幕
        String dir = "D:\\0nRun\\springmvc\\pe-service\\src\\main\\resources\\pic\\visitor\\";
        String fileName1 = dir + cuprum + ".jpg";
        String fileName2 = dir + paper + ".jpg";
        JLabel left = new JLabel(new ImageIcon(fileName1), JLabel.CENTER);
        JLabel right = new JLabel(new ImageIcon(fileName2), JLabel.CENTER);
        centerJp.add(left);
        centerJp.add(right);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


    @Override
    public void itemStateChanged(ItemEvent e) {

        JRadioButton jrb = (JRadioButton) e.getSource();
        if(jrb.isSelected()){
            if("造币公司".equalsIgnoreCase(jrb.getText())){
                select = (os.accept(visitor2)).split(" ");
            }else{
                select = (os.accept(visitor1)).split(" ");
            }
            showPicture(select[0], select[1]);
        }

    }
}





