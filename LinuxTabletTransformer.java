import javax.swing.SpringLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
//import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.SwingWorker;
import javax.swing.text.DefaultCaret;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;

import java.util.List;
import java.util.ArrayList;
  
public class LinuxTabletTransformer
{   
   public static void main(String[] args)
   {     
      appWindow instance = new appWindow();  
   }
   
   public static class appWindow extends JFrame
   {
      private JPanel             jpFrame;
      private static JLabel      jlAPX;
      private JComboBox          jcbSBK;
      private JTabbedPane        jtpTabs;
      private JLabel             jlHeader;
      private MyChangeListener   changeListener;
      private MyActionListener   actionListener;
      private MyAPXListener      apxListener;
      public appWindow()
      {
         SpringLayout layout = new SpringLayout();
         String[] models = {"SBK 1", "SBK 2"};
         
         jlAPX = new JLabel();
         jlAPX.setHorizontalAlignment(JLabel.RIGHT);
         jlAPX.setFont(new Font("Verdana", Font.BOLD, 10));
         ArrayList<String> command = new ArrayList<String>();
         command.add(".\\bins\\detectAPX.bat");
         new MyAPXListener(command).execute();
         
         jcbSBK = new JComboBox<String>(models);
         
         jtpTabs = new JTabbedPane();
         
         jlHeader = new JLabel("TF-101 Linux Installer");
         jlHeader.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 28));
         jlHeader.setForeground(new Color(255, 106, 0));
         
         jpFrame = new JPanel();
         
         actionListener = new MyActionListener();
         changeListener = new MyChangeListener();
         
         
            
         createInfoTab();
         createPartitionTab();
         createFlashTab();
         createDriversTab();
         
         jtpTabs.addTab("Info", jtaInfoTab);
         jtpTabs.addTab("Partition", jpPartitionTab);
         jtpTabs.addTab("Flash", jpFlashTab);
         jtpTabs.addTab("Drivers", jpDriversTab);
         
         jpFrame.add(jlAPX);
         layout.putConstraint(SpringLayout.NORTH, jlAPX, 5, SpringLayout.NORTH, jpFrame);
         layout.putConstraint(SpringLayout.EAST, jlAPX, -5, SpringLayout.EAST, jpFrame);
         layout.putConstraint(SpringLayout.WEST, jlAPX, 370, SpringLayout.WEST, jpFrame);
         jpFrame.add(jcbSBK);
         layout.putConstraint(SpringLayout.NORTH, jcbSBK, 5, SpringLayout.SOUTH, jlAPX);
         layout.putConstraint(SpringLayout.EAST, jcbSBK, -5, SpringLayout.EAST, jpFrame);
         layout.putConstraint(SpringLayout.WEST, jcbSBK, 0, SpringLayout.WEST, jlAPX);
         jpFrame.add(jtpTabs);
         layout.putConstraint(SpringLayout.NORTH, jtpTabs, 5, SpringLayout.SOUTH, jcbSBK);
         layout.putConstraint(SpringLayout.SOUTH, jtpTabs, -5, SpringLayout.SOUTH, jpFrame);
         layout.putConstraint(SpringLayout.EAST, jtpTabs, -5, SpringLayout.EAST, jpFrame);
         layout.putConstraint(SpringLayout.WEST, jtpTabs, 5, SpringLayout.WEST, jpFrame);
         jpFrame.add(jlHeader);
         layout.putConstraint(SpringLayout.NORTH, jlHeader, 5, SpringLayout.NORTH, jpFrame);
         layout.putConstraint(SpringLayout.SOUTH, jlHeader, -5, SpringLayout.NORTH, jtpTabs);
         layout.putConstraint(SpringLayout.EAST, jlHeader, -5, SpringLayout.EAST, jcbSBK);
         layout.putConstraint(SpringLayout.WEST, jlHeader, 5, SpringLayout.WEST, jpFrame);
      
         jpFrame.setLayout(layout);
         
         this.setLayout(new BorderLayout());
         this.add(jpFrame, BorderLayout.CENTER);
         
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         this.setSize(500, 600);
         this.setResizable(false);
         this.setTitle("Linux Tablet Transformer");
         this.setVisible(true);
      }
   
      private JTextArea jtaInfoTab;
      private void createInfoTab()
      {
         jtaInfoTab = new JTextArea();
         jtaInfoTab.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
         jtaInfoTab.setEditable(false);
         jtaInfoTab.setLineWrap(true);
         jtaInfoTab.append("This program installs Linux (or other Android ROMs) on your ASUS TF-101 Tablet.\n");
         jtaInfoTab.append("To install Linux/Custom ROMs go to the Flash Tab and select an option from the drop \ndown menu of the OS Area.\n");
         jtaInfoTab.append("        * Choose \'Android\' to install a Custom ROM only.\n");
         jtaInfoTab.append("        * Choose \'Android/Linux\' to install Android on the main partition and\n               Linux on the recovery partition.\n");
         jtaInfoTab.append("        * Choose \'Linux\' to install Linux only.\n");
         jtaInfoTab.append("        * Choose \'Linux/Android\' to install Linux on the main partition and\n               Android on the recovery partition.\n");
         jtaInfoTab.append("To choose your Android ROM, place an img file in the android folder of the install \ndirectory and rename it system.img.\n");
         jtaInfoTab.append("To choose your Linux Distro, place an img file in the linux folder of the install directory \nand rename it linux-distro.img.\n");
         jtaInfoTab.append("To install Recovery/Linux Kernels go to the Flash Tab and select an option from the \ndrop down menu of the Recovery Area.\n");
         jtaInfoTab.append("        * Choose \'CWM Recovery\' to install ClockWorkMod Recovery.\n");
         jtaInfoTab.append("        * Choose \'TWRP Recovery\' to install TeamWinRecoveryProject Recovery.\n");
         jtaInfoTab.append("        * Choose \'Linux Kernel\' to install/reinstall the Linux Kernel.\n");
         jtaInfoTab.append("To choose what to flash to your Recovery partition, place an img file in the recovery \nfolder of the install directory rename it cwmr-recovery.img or twrp-recovery.img or \nlinux-kernel.img accordingly.\n");
      }
      
      private JPanel    jpPartitionTab;
      private JPanel    jpAndroidPartition;
      private JSlider   jsAndroidSize;
      private JLabel    jlAndroidSizeVal;
      private JPanel    jpLinuxPartition;
      private JSlider   jsLinuxSize;
      private JLabel    jlLinuxSizeVal;
      private void createPartitionTab()
      {  
         Dimension d = new Dimension(410, 50);
         Dimension d2 = new Dimension(450, 60);
         Font f = new Font("Verdana", Font.BOLD, 18);
         
         jsAndroidSize = new JSlider(JSlider.HORIZONTAL, 0, 11, 5);
         jsAndroidSize.setPreferredSize(d);
         jsAndroidSize.addChangeListener(changeListener);
         
         jlAndroidSizeVal = new JLabel("5");
         jlAndroidSizeVal.setFont(f);
         
         jpAndroidPartition = new JPanel(new BorderLayout());
         jpAndroidPartition.setPreferredSize(d2);
         jpAndroidPartition.setBorder(BorderFactory.createTitledBorder("Android Partition Size"));
         jpAndroidPartition.add(jsAndroidSize, BorderLayout.WEST);
         jpAndroidPartition.add(jlAndroidSizeVal);
         
         jsLinuxSize = new JSlider(JSlider.HORIZONTAL, 0, 11, 6);
         jsLinuxSize.setPreferredSize(d);
         jsLinuxSize.addChangeListener(changeListener);
         
         jlLinuxSizeVal = new JLabel("6");
         jlLinuxSizeVal.setFont(f);
         
         jpLinuxPartition = new JPanel(new BorderLayout());
         jpLinuxPartition.setPreferredSize(d2);
         jpLinuxPartition.setBorder(BorderFactory.createTitledBorder("Linux Partition Size"));
         jpLinuxPartition.add(jsLinuxSize, BorderLayout.WEST);
         jpLinuxPartition.add(jlLinuxSizeVal);
                     
         jpPartitionTab = new JPanel();
         jpPartitionTab.add(jpAndroidPartition, BorderLayout.NORTH);
         jpPartitionTab.add(jpLinuxPartition, BorderLayout.SOUTH);
      }
      
      private JPanel       jpOS;
      private JTextArea    jtaOSInfo;
      private JButton      jbOSFlash;
      private JComboBox    jcbOSOptions;
      private JTextArea    jtaRecoveryInfo;
      private JPanel       jpRecovery;
      private JButton      jbRecoveryFlash;
      private JComboBox    jcbRecoveryOptions;
      private JPanel       jpFlashTab;
      private JTextArea    jtaConsole;
      private JScrollPane  jspScroll;
      private void createFlashTab()
      {
         SpringLayout layout = new SpringLayout();
         
         jpOS = new JPanel();
         Color color = jpOS.getBackground();
         String[] rootOptions = {"Android", "Android/Linux", "Linux", "Linux/Android"};
         
         jtaOSInfo = new JTextArea("This will flash your\nTF-101 with the\nselected option.\n");
         jtaOSInfo.setEditable(false);
         jtaOSInfo.setLineWrap(true);
         jtaOSInfo.setPreferredSize(new Dimension(120, 105));
         jtaOSInfo.setBackground(color);
         
         jcbOSOptions = new JComboBox<String>(rootOptions);
         jcbOSOptions.setPreferredSize(new Dimension(120, 25));
         
         jbOSFlash = new JButton("Flash");
         jbOSFlash.setPreferredSize(new Dimension(120, 55));
         jbOSFlash.addActionListener(actionListener);
         
         jpOS.setBorder(BorderFactory.createTitledBorder("OS"));
         jpOS.add(jtaOSInfo, BorderLayout.NORTH);
         jpOS.add(jcbOSOptions, BorderLayout.CENTER);
         jpOS.add(jbOSFlash, BorderLayout.SOUTH);
         
         String[] recoveryOptions = {"CWM Recovery", "TWRP Recovery", "Linux Kernel"};
         jtaRecoveryInfo = new JTextArea("This will flash only\nthe recovery partition\nof your TF-101.");
         jtaRecoveryInfo.setEditable(false);
         jtaRecoveryInfo.setLineWrap(true);
         jtaRecoveryInfo.setPreferredSize(new Dimension(120, 110));
         jtaRecoveryInfo.setBackground(color);
         
         jcbRecoveryOptions = new JComboBox<String>(recoveryOptions);
         jcbRecoveryOptions.setPreferredSize(new Dimension(120, 25));
         
         jbRecoveryFlash = new JButton("Flash");
         jbRecoveryFlash.setPreferredSize(new Dimension(120, 55));
         jbRecoveryFlash.addActionListener(actionListener);
         
         jpRecovery = new JPanel();
         jpRecovery.setBorder(BorderFactory.createTitledBorder("Recovery"));
         jpRecovery.add(jtaRecoveryInfo, BorderLayout.NORTH);
         jpRecovery.add(jcbRecoveryOptions, BorderLayout.CENTER);
         jpRecovery.add(jbRecoveryFlash, BorderLayout.SOUTH);
         
         jtaConsole = new JTextArea("Ready To Flash");
         jtaConsole.setEditable(false);
         jtaConsole.setLineWrap(true);
         DefaultCaret caret = (DefaultCaret)jtaConsole.getCaret();
         caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
         
         jspScroll = new JScrollPane(jtaConsole, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
         jspScroll.setPreferredSize(new Dimension(455, 370));
         
         jpFlashTab = new JPanel();
         jpFlashTab.add(jspScroll, BorderLayout.SOUTH);
         layout.putConstraint(SpringLayout.NORTH, jspScroll, 5, SpringLayout.NORTH, jpFlashTab);
         layout.putConstraint(SpringLayout.SOUTH, jspScroll, -5, SpringLayout.SOUTH, jpFlashTab);
         layout.putConstraint(SpringLayout.EAST, jspScroll, -5, SpringLayout.EAST, jpFlashTab);
         layout.putConstraint(SpringLayout.WEST, jspScroll, 152, SpringLayout.WEST, jpFlashTab);
         jpFlashTab.add(jpOS, BorderLayout.NORTH);
         layout.putConstraint(SpringLayout.NORTH, jpOS, 5, SpringLayout.NORTH, jpFlashTab);
         layout.putConstraint(SpringLayout.SOUTH, jpOS, -248, SpringLayout.SOUTH, jpFlashTab);
         layout.putConstraint(SpringLayout.EAST, jpOS, -5, SpringLayout.WEST, jspScroll);
         layout.putConstraint(SpringLayout.WEST, jpOS, 5, SpringLayout.WEST, jpFlashTab);
         jpFlashTab.add(jpRecovery, BorderLayout.CENTER);
         layout.putConstraint(SpringLayout.NORTH, jpRecovery, 5, SpringLayout.SOUTH, jpOS);
         layout.putConstraint(SpringLayout.SOUTH, jpRecovery, -5, SpringLayout.SOUTH, jpFlashTab);
         layout.putConstraint(SpringLayout.EAST, jpRecovery, -5, SpringLayout.WEST, jspScroll);
         layout.putConstraint(SpringLayout.WEST, jpRecovery, 5, SpringLayout.WEST, jpFlashTab);
         jpFlashTab.setLayout(layout);
      }
      
      private JPanel    jpDriver32;
      private JTextArea jtaDriver32Info;
      private JButton   jbDriver32;
      private JPanel    jpDriver64;
      private JTextArea jtaDriver64Info;
      private JButton   jbDriver64;
      private JPanel    jpDriversTab;
      private void createDriversTab()
      {
         jpDriversTab = new JPanel();
         Color c = jpDriversTab.getBackground();
         Dimension d = new Dimension(220, 60);
         
         jtaDriver32Info = new JTextArea("    Installs NVIDIA's 32-bit APX Drivers.");
         jtaDriver32Info.setEditable(false);
         jtaDriver32Info.setPreferredSize(d);
         jtaDriver32Info.setBackground(c);
         
         jbDriver32 = new JButton("Install");
         jbDriver32.setPreferredSize(d);
         jbDriver32.addActionListener(actionListener);
         
         jpDriver32 = new JPanel(new BorderLayout());
         jpDriver32.setBorder(BorderFactory.createTitledBorder("32 bit"));
         jpDriver32.add(jtaDriver32Info);
         jpDriver32.add(jbDriver32, BorderLayout.SOUTH);
         
         jtaDriver64Info = new JTextArea("    Installs NVIDIA's 64-bit APX Drivers.");
         jtaDriver64Info.setEditable(false);
         jtaDriver64Info.setPreferredSize(d);
         jtaDriver64Info.setBackground(c);
         
         jbDriver64 = new JButton("Install");
         jbDriver64.setPreferredSize(d);
         jbDriver64.addActionListener(actionListener);
         
         jpDriver64 = new JPanel(new BorderLayout());
         jpDriver64.setBorder(BorderFactory.createTitledBorder("64 bit"));
         jpDriver64.add(jtaDriver64Info);
         jpDriver64.add(jbDriver64, BorderLayout.SOUTH);
         
         jpDriversTab.add(jpDriver32);
         jpDriversTab.add(jpDriver64);
         
      }
        
      private void flashOS()
      {
         if (jcbSBK.getSelectedItem() == "SBK 2")
         {
            jtaConsole.setText("This function is not set up to work with SBK 2 yet!\n");
            return;
         }
            
         jtaConsole.setText("Flashing OS!\n");
            
         ArrayList<String> command = new ArrayList<String>();
         command.add(".\\bins\\powershell");
         command.add("cd .\\bins");
               
         String cfg = "..\\dualboot\\dualboot.cfg";
               
         if (jcbOSOptions.getSelectedItem() == "Android")
            cfg = "..\\android\\android.cfg";
         if (jcbOSOptions.getSelectedItem() == "Android/Linux")
            cfg = "..\\dualboot\\dualboot.cfg";
         if (jcbOSOptions.getSelectedItem() == "Linux")
            cfg = "..\\linux\\linux.cfg";
         if (jcbOSOptions.getSelectedItem() == "Linux/Android")
            cfg = "..\\dualboot\\linux-dualboot.cfg";
               
         command.add(".\\nvflash --bct transformer.bct --setbct --configfile " + cfg + " --create "
                  + "--bl bootloader.bin --odmdata 0x300D8011 --sbk 0x1682CCD8 0x8A1A43EA 0xA532EEB6 0xECFE1D98 --go");
            
         new ProcessWorker(command, jtaConsole).execute();
      }
        
      private void flashRecovery()
      {
         jtaConsole.setText("Flashing Recovery Partition!\n\n");
               
         int sbk;
         if (jcbSBK.getSelectedItem() == "SBK 2")
            sbk = 2;
         else
            sbk = 1;
               
         ArrayList<String> command = new ArrayList<String>();
         command.add(".\\bins\\powershell");
         command.add("cd .\\bins");
         command.add(".\\wheelie -" + sbk + " -o 0x300d8011 --bl bootloader.bin -c transformer.bct");
            
         String img = "cwmr-recovery.img";
               
         if (jcbRecoveryOptions.getSelectedItem() == "CWM Recovery")
            img = "cwmr-recovery.img";
         else if (jcbRecoveryOptions.getSelectedItem() == "TWRP Recovery")
            img = "twrp-recovery.img";
         else if (jcbRecoveryOptions.getSelectedItem() == "Linux Kernel")
            img = "linux-kernel.img";
               
         command.add(".\\nvflash -r --download 6 ..\\recovery\\" + img);
         command.add(".\\nvflash -r --go");
            
         new ProcessWorker(command, jtaConsole).execute();
      }
        
      private void installDrivers32()
      {
         ArrayList<String> command = new ArrayList<String>();
         
         command.add(".\\bins\\powershell");
         command.add(".\\drivers\\DPInst32.exe");
         
         new ProcessWorker(command, jtaConsole).execute();
      }
       
      private void installDrivers64()
      {
         ArrayList<String> command = new ArrayList<String>();
         
         command.add(".\\bins\\powershell");
         command.add(".\\drivers\\DPInst64.exe");
         
         new ProcessWorker(command, jtaConsole).execute();
      }
        
      private void adjustPartitionSizes(JSlider source)
      {
         if (source == jsAndroidSize)
         {
            int srcVal = source.getValue();
            int val = 11 - srcVal;
            
            jlAndroidSizeVal.setText("" + srcVal);
            
            jsLinuxSize.setValue(val);
            jlLinuxSizeVal.setText("" + val);
         }
         else if (source == jsLinuxSize)
         {
            int srcVal = source.getValue();
            int val = 11 - srcVal;
            
            jlLinuxSizeVal.setText("" + srcVal);
            
            jsAndroidSize.setValue(val);
            jlAndroidSizeVal.setText("" + val);
         }
      }
        
      private static void checkForAPXDevice(String val)
      {
         //System.out.println(string + "\n");
               
         if (val.contains("No matching devices found."))
         {
            jlAPX.setForeground(Color.RED);
            jlAPX.setText("NO Device Detected!");
         }
         else
         {
            jlAPX.setForeground(new Color(0, 200, 0));
            jlAPX.setText("Device Detected!");
         }
      }
        
      private class MyActionListener implements ActionListener
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {     
            if (e.getSource() == jbOSFlash)
               flashOS();
            else if (e.getSource() == jbRecoveryFlash)
               flashRecovery();
            else if (e.getSource() == jbDriver32)
               installDrivers32();
            else if (e.getSource() == jbDriver64)
               installDrivers64();
         }
      }
      
      private class MyChangeListener implements ChangeListener
      {   
         @Override
         public void stateChanged(ChangeEvent e)
         {
            JSlider source = (JSlider)e.getSource();
            adjustPartitionSizes(source);
         }
      }
   
      private static class MyAPXListener extends SwingWorker<Void, String>
      {
         Process p;
         private List<String> process;
      
         public MyAPXListener(List<String> command)
         {
            this.process = command;
            
            Runtime.getRuntime().addShutdownHook(
                  new Thread()
                  {
                     public void run()
                     {
                        if (p != null)
                           p.destroy();
                     }
                  });
         }
      
         @Override
         protected Void doInBackground() throws Exception
         {
            p = new ProcessBuilder(process).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
         
            while ((line = br.readLine()) != null)
               publish(line);
         
            br.close();
            return null;
         }
      
         @Override
         protected void process(List<String> chunks)
         {
            for (String string : chunks)
            {
               checkForAPXDevice(string);
            }
         }
      }
   }

   private static class ProcessWorker extends SwingWorker<Void, String>
   {
      private Process p;
      private JTextArea jta;
      private List<String> process;
      private File dir;
      
      public ProcessWorker(List<String> command, JTextArea ta)
      {
         this.process = command;
         
         for (int i = 0; i < process.size(); ++i)
         {
            if (i != 0)
               process.set(i, process.get(i).concat("\n"));
         }
         
         this.jta = ta;
         Runtime.getRuntime().addShutdownHook(
               new Thread()
               {
                  public void run()
                  {
                     if (p != null)
                        p.destroy();
                  }
               });
      }
      
      @Override
      protected Void doInBackground() throws Exception
      {
         p = new ProcessBuilder(process).start();
         BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
         String line;
         
         while ((line = br.readLine()) != null)
            publish(line);
         
         br.close();
         return null;
      }
      
      @Override
      protected void process(List<String> chunks)
      {
         if (jta != null)
         {
            for (String string : chunks)
               jta.append(string + "\n");
         }
      }
   }
}