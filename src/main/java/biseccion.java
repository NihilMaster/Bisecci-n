import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author M
 */

public class biseccion extends JFrame {
    
    ////////////////////////////////////////////////////////////////
    /////////////La función se agrega a continuación////////////////
    ////////////////////////////////////////////////////////////////
    private static final String FUN_STR = "e^(-x) + cos(3x) - 0.5 ";
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    private static float f(float a){
        return (float)(Math.pow((Math.E),(-a)) + Math.cos(3*a) - 0.5); 
    }
    ////////////////////////////////////////////////////////////////
    ////////////////////// No. Iteraciones /////////////////////////
    private static final int IT = 10;
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    
    JFrame frame;
    JPanel panelSuperior, panelInferior;
    JButton tabtt, robtt;
    JLabel descrip, albl, blbl, clbl, flbl;
    JTextField atxt, btxt, ctxt;
    JTable table1, table2;
    
    private static String fin = "";
    
    public biseccion() {
        super("Bisecci\u00F3n");
        
        // PANEL SUPERIOR
        panelSuperior = new JPanel ();
        panelSuperior.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelSuperior.setLayout(new GridLayout(3,2,8,8));
        albl = new JLabel("Ingrese el menor valor:");
        atxt = new JTextField("0",20);
        blbl = new JLabel("Ingrese el mayor valor:");
        btxt = new JTextField("0",20);
        clbl = new JLabel("Ingrese la tolerancia:");
        ctxt = new JTextField("0.01",20);
        clbl.setVisible(false); ctxt.setVisible(false);
        panelSuperior.add(albl); panelSuperior.add(atxt);
        panelSuperior.add(blbl); panelSuperior.add(btxt);
        panelSuperior.add(clbl); panelSuperior.add(ctxt);
        
    
        // PANEL INFERIOR
        panelInferior= new JPanel();
        panelInferior.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.PAGE_AXIS));
        tabtt = new JButton("Tabular");
        robtt = new JButton("Encontrar ra\u00EDz");
        table1 = new JTable(); table2 = new JTable();
        flbl = new JLabel("");
        JScrollPane scrollPanel1 = new JScrollPane(table1);
        JScrollPane scrollPanel2 = new JScrollPane(table2);
        // Estilo
        scrollPanel2.setVisible(false);
        tabtt.setBackground(Color.CYAN);
        robtt.setVisible(false); robtt.setBackground(Color.GREEN);
        // Adición
        panelInferior.add(tabtt);
        panelInferior.add(robtt);
        panelInferior.add(scrollPanel1); panelInferior.add(scrollPanel2);
        panelInferior.add(flbl);
        
        // DESCRIPCIÓN
        descrip = new JLabel("Seleccione el intervalo a tabular.");
        descrip.setHorizontalAlignment(SwingConstants.LEFT);
        descrip.setFont(new Font("Arial", Font.BOLD, 20));
        
        // GENERAL
        frame =new JFrame("Bisecci\u00F3n     |     Mateo L. - Weimar J.");
        frame.setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));
        frame.add(descrip);
        frame.add(panelSuperior);
        frame.add(panelInferior);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        //Escuchadores
        tabtt.addActionListener((ActionEvent e) -> {
            String astr = atxt.getText();
            String bstr = btxt.getText();
            Object[] col = {"x", ""+FUN_STR,};
            Object[][] dat = tab1(Float.parseFloat(astr), Float.parseFloat(bstr));
            DefaultTableModel dmt = new DefaultTableModel(dat,col);
            table1.setModel(dmt);
            // Cambio de estilo
            panelSuperior.setBackground(Color.GRAY); panelInferior.setBackground(Color.GRAY);
            atxt.setText("0"); btxt.setText("0");
            descrip.setText("Seleccione el intervalo para encontrar la ra\u00EDz");
            tabtt.setVisible(false); robtt.setVisible(true);
            clbl.setVisible(true); ctxt.setVisible(true);
        });
        
        robtt.addActionListener((ActionEvent e) -> {
            scrollPanel1.setVisible(false); scrollPanel2.setVisible(true);
            String astr = atxt.getText();
            String bstr = btxt.getText();
            String cstr = ctxt.getText();
            if (f(Float.parseFloat(astr))*f(Float.parseFloat(bstr))>0){
                showMessageDialog(null, "El rango seleccionado no pasa por el 0\nProcure que ambas funciones, f(a) y f(b), resulten con diferentes signos.");
            }else{
                Object[] col = {"a", "b", "x", "f(a)", "f(b)", "f(x)"};
                Object[][] dat = tab2(Float.parseFloat(astr), Float.parseFloat(bstr), Float.parseFloat(cstr));
                DefaultTableModel mdma = new DefaultTableModel(dat,col);
                table2.setModel(mdma);
                flbl.setText(fin);
                showMessageDialog(null, fin);
            }
        });
    }
    
    private static Object[][] tab1(float a, float b){
        Object[][] s = new Object[IT][2];
        float iter = (b-a)/IT;
        System.out.println("\nx\t" + FUN_STR + "\n");
        for (int i=0; i<IT; i++){
            s[i][0]=a; s[i][1]=f(a);
            System.out.println(s[i][0] + "\t" + s[i][1]);
            a += iter;
        }
        return s;
    }
    
    private static Object[][] tab2(float a, float b, float t){
        Object[][] s = new Object[100][6]; 
        int i = 0;
        System.out.println("\na\tb\tx\tf(a)\t\tf(b)\t\tf(x)\n");
        do{
            float xr = (float)((a+b)/2.0);
            s[i][0]=a; s[i][1]=b; s[i][2]=xr; s[i][3]=f(a); s[i][4]=f(b); s[i][5]=f(xr);
            System.out.println(a + "\t" + b + "\t" + xr + "\t" + f(a) + "\t" + f(b) + "\t" + f(xr));
            if (Math.abs(f(xr)) <= t) { // xr sería la raiz de f
                System.out.println("\n\nPara una tolerancia " + t + " la ra\u00EDz de f es " + xr);
                fin = "\n\nPara una tolerancia " + t + " la ra\u00EDz de la funci\u00F3n es " + xr;
                break;
            }else{
                if(f(xr) * f(a) > 0){
                    a = xr;
                }else if(f(xr) * f(b) > 0){
                    b = xr;
                }
            }
            i++;
        }while(true);
        return s;
    }
    
    public static void main(String[] args) throws IOException{
        biseccion run = new biseccion();
        System.out.println(""+run);
    }
}
