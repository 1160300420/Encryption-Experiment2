package ServerAdmin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import DBTool.DBUtil;

public class adminmain extends JFrame {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * 表格显示内容
   */
  private final List<String> TITLES = Arrays.asList("ID", "name", "details");
  private Vector<Vector<String>> dataModel = new Vector<Vector<String>>();
  private QueryItem id = new QueryItem("用户ID：", 15);
  private QueryItem pw = new QueryItem("用户密码：", 15);
  private QueryItem cer_user_op = new QueryItem("操作用户ID：", 7);
  private QueryItem cer_obj_op = new QueryItem("商品ID：", 7);
  private QueryItem cer_order_op = new QueryItem("订单ID：", 7);
  private QueryItem goods_mount = new QueryItem("商品数量：", 10);
  /**
   * 管理员查询信息条目
   */
  private JButton queryBtn = new JButton("查询");
  /**
   * 管理员删除信息条目
   */
  private JButton deleteBtn = new JButton("删除");
  /**
   * 管理员修改信息条目
   */
  private JButton modifyBtn = new JButton("修改");
  /**
   * 查询结果显示
   */
  private MyTable table;
  /**
   * 数据库连接
   */
  private Connection conn = DBUtil.getConnection();
  /**
   * 日志记录
   */
  public static Logger logger= Logger.getLogger("log");;
  public static void main(String[] args) throws ClassNotFoundException, SQLException, SecurityException, IOException {
    adminmain frame = new adminmain("OnlineShopAdministor");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setMinimumSize(new Dimension(750, 500));
    frame.setVisible(true);
    frame.setResizable(false);
    final FileHandler fileHandler = getHandler();
    logger.setLevel(Level.ALL);
    fileHandler.setFormatter(new LogFormatter());
    logger.addHandler(fileHandler);
    logger.info("！！！！！！！！！程序启动！！！！！！！！！");
  }
  
  //构造函数，负责创建用户界面
  public adminmain(String title) {
    super(title);
    
    Vector<String> titles = new Vector<String>(TITLES);
    table = new MyTable(dataModel, titles);
    table.getColumnModel().getColumn(2).setPreferredWidth(30);
    /**
          * 显示管理员登陆部分
     */
    JPanel controlPanel = new JPanel();
    controlPanel.setLayout(new FlowLayout());
    controlPanel.add(id);
    controlPanel.add(pw);
    controlPanel.add(goods_mount);
    /**
          * 显示操作具体输入
     */
    JPanel dePanel = new JPanel();
    dePanel.setLayout(new FlowLayout());
    dePanel.add(cer_user_op);
    dePanel.add(cer_obj_op);
    dePanel.add(cer_order_op);
    
    /**
          * 显示操作部分
     */
    JPanel opPanel = new JPanel();
    opPanel.add(deleteBtn);
    opPanel.add(queryBtn);
    opPanel.add(modifyBtn);
    controlPanel.add(opPanel, BorderLayout.NORTH);
    controlPanel.setPreferredSize(new Dimension(0, 160));
    /**
     * 显示查询得到的信息
     */
    JPanel tablePanel = new JPanel();
    tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
    tablePanel.add(Box.createRigidArea(new Dimension(0, 20)));
    tablePanel.add(table.getTableHeader());
    tablePanel.add(new JScrollPane(table));
    /**
           *显示修改项
     */
    JPanel container = new JPanel();
    container.setLayout(new BorderLayout());
    container.add(dePanel, BorderLayout.NORTH);
    container.add(tablePanel, BorderLayout.CENTER);
    
    this.add(controlPanel, BorderLayout.NORTH);
    this.add(container, BorderLayout.CENTER);
    this.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.WEST);
    this.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.EAST);
    this.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.SOUTH);
    
    setActionListener();
  }
  
  private void setActionListener() {
    
    //管理员根据关键字查询
    queryBtn.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        //验证管理员身份
        if (id.isSelected() && pw.isSelected()&&id.getText().equals("admin")&&pw.getText().equals("admin")) {
          dataModel.clear();
          if (cer_user_op.isSelected()) {
            //查询用户列表
            //查询所有
            if (cer_user_op.getText() == null || cer_user_op.getText().equals("")) {
              try {
                new adminfunction().query_user(conn,"",dataModel);
                logger.info("管理员查询所有用户列表");
              } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
              }
            } else {
            //查询特定
              try {
                new adminfunction().query_user(conn, cer_user_op.getText(),dataModel);
                logger.info("管理员查询用户："+cer_user_op.getText());
              } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
              }
            }
          } else if (cer_obj_op.isSelected()) {
            //查询商品列表
            //查询所有
            if (cer_obj_op.getText() == null || cer_obj_op.getText().equals("")) {
              try {
                new adminfunction().query_goods(conn, "", dataModel);
                logger.info("管理员查询所有商品");
              } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
              }
            } else {
            //查询特定
              try {
                new adminfunction().query_goods(conn, cer_obj_op.getText(), dataModel);
                logger.info("管理员查询商品："+cer_obj_op.getText());
              } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
              }
            }
          } else if (cer_order_op.isSelected()) {
            //查询订单列表
            //查询所有
            if (cer_order_op.getText() == null || cer_order_op.getText().equals("")) {
              try {
                new adminfunction().query_order(conn, "", dataModel);
                logger.info("管理员查询所有订单列表");
              } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
              }
            } else {
            //查询特定
              try {
                new adminfunction().query_order(conn, cer_order_op.getText(), dataModel);
                logger.info("管理员查询用户："+cer_user_op.getText()+"的订单列表");
              } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
              }
            }
          }
        }
        //更新表格
        table.validate();
        table.updateUI();
      }
    });
    
    //管理员修改
    modifyBtn.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent arg0) {
      //验证管理员身份
        if (id.isSelected() && pw.isSelected()&&id.getText().equals("admin")&&pw.getText().equals("admin")) {
          dataModel.clear();
          if(cer_obj_op.isSelected()&&goods_mount.isSelected()) {
            //修改商品数量
            try {
              new adminfunction().modify_goods(conn, cer_obj_op.getText(), goods_mount.getText());
              logger.info("管理员修改商品："+cer_obj_op.getText()+"的数量："+goods_mount.getText());
            } catch (SQLException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
       }
        table.validate();
        table.updateUI(); 
      }
    });
    //管理员删除用户
    deleteBtn.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
      //验证管理员身份
        if (id.isSelected() && pw.isSelected()&&id.getText().equals("admin")&&pw.getText().equals("admin")) {
          //删除用户
          if (cer_user_op.isSelected()) {
            if (cer_user_op.getText() != null && !cer_user_op.getText().equals("")) {
                try {
                  dataModel.clear();
                  new adminfunction().delete(conn, cer_user_op);
                  logger.info("管理员删除用户："+cer_user_op.getText());
                } catch (SQLException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
                }
            }
          }
       }
        //更新表格
        table.validate();
        table.updateUI();
      }
    });
  }
  public static FileHandler getHandler() throws SecurityException, IOException {
    return new FileHandler("src/log.txt");
  }
}

/** 查询项目
* 将复选框、标签、文本框组合成一个组件
* 对外提供获取文本和选中状态的两个方法
*/
class QueryItem extends JPanel {
  private JCheckBox checkbox;
  private JLabel label;
  private JTextField textfield;
  
  public QueryItem(String labelText, int textWidth) {
    checkbox = new JCheckBox();
    label = new JLabel(labelText);
    textfield = new JTextField(textWidth);
    this.add(checkbox);
    this.add(label);
    this.add(textfield);
  }
  
  public boolean isSelected() {
    return checkbox.isSelected();
  }
  
  public String getText() {
    return textfield.getText();
  }
  
}

/* 同样是查询项目
* 这是用于查询年龄范围的组件，包含了两个文本框
* 因此特殊处理，并增加了获取第二个文本框内容的方法
*/
class QueryItem2 extends QueryItem {
  private JLabel label2;
  private JTextField textfield2;
  
  public QueryItem2(String labelText, String labelText2, int textWidth) {
    super(labelText, textWidth);
    label2 = new JLabel(labelText2);
    textfield2 = new JTextField(textWidth);
    this.add(label2);
    this.add(textfield2);
  }
  
  public String getText2() {
    return textfield2.getText();
  }
}

/* 表格组件
* 重载了 JTable 的 isCellEditable 方法
* 目的是防止编辑 Sid 字段，禁止修改主键
*/
class MyTable extends JTable {
  public MyTable(Vector data, Vector title) {
    super(data, title);
  }
  
  @Override
  public boolean isCellEditable(int row, int column) {
    if (column == 0)
      return false;
    else
      return true;
  }
}
