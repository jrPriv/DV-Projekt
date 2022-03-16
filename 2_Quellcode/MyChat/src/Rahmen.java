import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Rahmen
{
   static int count =0;
	public static JFrame inFrame(String title, JPanel jp, int width,
	int height) {
		count++;
		JFrame frame = new JFrame(title);
		frame.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e)
		{
			if (--count == 0)
				System.exit(0);
		}
		});
		frame.getContentPane().add(jp,BorderLayout.CENTER);
		frame.setSize(width,height);
		frame.setResizable(true);
		frame.setVisible(true);
		return frame;
	}
}