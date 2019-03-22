//Bereket Mengiste
//bamengiste15@ole.augie.edu
//BlackJack.java
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
class MyJFrame extends JFrame implements ActionListener
{
		private JLabel[] lblPCard = new JLabel[7];
		private JLabel[] lblDCard = new JLabel[7];
		private JPanel pnlCenter= new JPanel();
		
		private JButton btnDeal= new JButton("Deal");
		private JButton btnPlayer= new JButton("Player");
		private JButton btnDealer= new JButton("Dealer");
		private JButton btnNew= new JButton("New");
		private JPanel pnlSouth= new JPanel();
		
		private DeckOfCards cards=new DeckOfCards();
		
		private Vector<String> dealerCard= new Vector<String>();
	 	private Vector<String> playerCard= new Vector<String>();
		
		public MyJFrame()
		{
			
			for (int i=0; i<7; ++i)
				lblPCard[i]=new JLabel("Player");
			for (int i=0; i<7; ++i)
				lblDCard[i]=new JLabel("Dealer");
			cards.shuffle();
			add(pnlCenter, BorderLayout.CENTER);
			add(pnlSouth, BorderLayout.SOUTH);
			addControlsPnlCenter();
			addControlsPnlSouth();
			registerListeners();
			btnDeal.setEnabled(true);
			btnPlayer.setEnabled(false);
			btnDealer.setEnabled(false);
			btnNew.setEnabled(false);
		}
		public void addControlsPnlCenter()
		{
			pnlCenter.setLayout(new GridLayout(2,7));
			for (int i=0; i<7; ++i)
				pnlCenter.add(lblPCard[i]);
			for (int i=0; i<7; ++i)
				pnlCenter.add(lblDCard[i]);
		}
		public void addControlsPnlSouth()
		{
			pnlSouth.setLayout(new FlowLayout());
			pnlSouth.add(btnDeal);
			pnlSouth.add(btnPlayer);
			pnlSouth.add(btnDealer);
			pnlSouth.add(btnNew);
		}
		public void registerListeners()
		{
			btnDeal.addActionListener(this);
			btnPlayer.addActionListener(this);
			btnDealer.addActionListener(this);
			btnNew.addActionListener(this);
		}
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource()==btnDeal)
				deal();
			else if(e.getSource()==btnPlayer)
				player();
			else if(e.getSource()==btnDealer)
				dealer();
			else if(e.getSource()==btnNew)
			{
				newDeal();	
			}
		}
		public void deal()
		{
			dealerCard.add(cards.deal());
			dealerCard.add(cards.deal());
			playerCard.add(cards.deal());
			playerCard.add(cards.deal());
			displayPlayer();
			displayDealer(true);
			btnDeal.setEnabled(false);
			btnPlayer.setEnabled(true);
		}
		public void player()
		{
			String hitStay= JOptionPane.showInputDialog(null, 
			"You have total "+total(playerCard)+". Hit or Stay H/S?");
			if (hitStay.charAt(0)=='H' || hitStay.charAt(0)=='h')
			{
				playerCard.add(cards.deal());
				if(total(playerCard)>21)
				{
					JOptionPane.showMessageDialog(null, 
					"You Busted.");
					displayDealer(false);
					btnPlayer.setEnabled(false);
					btnDealer.setEnabled(false);
					btnNew.setEnabled(true);
				}
				displayPlayer();
			}
			else 
			{
				btnPlayer.setEnabled(false);
				btnDealer.setEnabled(true);
			}
		}
		public void dealer()
		{
			displayDealer(false);
			while( total(dealerCard)<17)
			{
				dealerCard.add(cards.deal());
				displayDealer(false);
				if(total(dealerCard)>21)
				{
					JOptionPane.showMessageDialog(null, 
					"Dealer Busted.");
					btnPlayer.setEnabled(false);
					btnDealer.setEnabled(false);
					btnNew.setEnabled(true);
				}
			}
			if(total(dealerCard)<=21)
				whoWin();
		}
		public void newDeal()
		{
			cards=new DeckOfCards();
			cards.shuffle();
			playerCard= new Vector<String>();
			dealerCard= new Vector<String>();
			btnDeal.setEnabled(true);
			btnPlayer.setEnabled(false);
			btnDealer.setEnabled(false);
			btnNew.setEnabled(false);
			int i=0;
			while(i<8)
			{
				lblPCard[i].setText("Player");
				lblDCard[i].setText("Dealer");
				lblPCard[i].setIcon(new ImageIcon(""));
				lblDCard[i].setIcon(new ImageIcon(""));
				i++;
			}
			btnDeal.setEnabled(true);
			btnPlayer.setEnabled(false);
			btnDealer.setEnabled(false);
			btnNew.setEnabled(false);
		}
		public void displayPlayer()
		{
			for(int i=0; i<playerCard.size(); i++)
			{
				lblPCard[i].setIcon(new ImageIcon("cardImages/"
									+playerCard.get(i)+".gif"));
				lblPCard[i].setText("");
			}
		}
		public void displayDealer(boolean x)
		{
			if(x)
			{
				lblDCard[0].setIcon(new ImageIcon("cardImages/"
									+dealerCard.get(0)+".gif"));
				lblDCard[1].setIcon(new ImageIcon("cardImages/card.gif"));
			}
			else
			{
				for(int i=0; i<dealerCard.size(); i++)
				{
					lblDCard[i].setIcon(new ImageIcon("cardImages/"
										+dealerCard.get(i)+".gif"));
					lblDCard[i].setText("");
				}
			}
		}
		public int total(Vector<String> v)
		{
			int result=0;
			for( int i=0; i<v.size(); i++)
			{
				if( v.get(i).startsWith("Ace")) result+=11;
				else if (v.get(i).startsWith("Two")) result+=2;
				else if (v.get(i).startsWith("Three")) result+=3;
				else if (v.get(i).startsWith("Four")) result+=4;
				else if (v.get(i).startsWith("Five")) result+=5;
				else if (v.get(i).startsWith("Six")) result+=6;
				else if (v.get(i).startsWith("Seven")) result+=7;
				else if (v.get(i).startsWith("Eight")) result+=8;
				else if (v.get(i).startsWith("Nine")) result+=9;
				else if (v.get(i).startsWith("Ten")) result+=10;
				else if (v.get(i).startsWith("Jack")) result+=10;
				else if (v.get(i).startsWith("Queen")) result+=10;
				else if (v.get(i).startsWith("King")) result+=10;
				else result+=0;
			}
			return result;
		}
		public void whoWin()
		{
			if(total(dealerCard)>total(playerCard))
			{
				JOptionPane.showMessageDialog(null, 
				"Dealer has "+total(dealerCard)+". You have "
											+total(playerCard));
				JOptionPane.showMessageDialog(null, 
				"You lost. Bye Bye");
				btnDealer.setEnabled(false);
				btnNew.setEnabled(true);
			}
			else if(total(playerCard)>total(dealerCard))
			{
				JOptionPane.showMessageDialog(null, "Dealer has "
				+total(dealerCard)+". You have "+total(playerCard));								
				JOptionPane.showMessageDialog(null, 
										"You won.");
				btnDealer.setEnabled(false);
				btnNew.setEnabled(true);
			}
			else 
			{
				JOptionPane.showMessageDialog(null, 
				"Dealer has "+total(dealerCard)+". You have "
											+total(playerCard));
				JOptionPane.showMessageDialog(null, 
				"You tied.");
				btnDealer.setEnabled(false);
				btnNew.setEnabled(true);
			}
		}
}
public class BlackJack
{
		public static void main(String[] args)
		{
			MyJFrame f=new MyJFrame();
			f.setTitle("BlackJack");
			f.setSize(550,325);
			f.setLocationRelativeTo(null);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setVisible(true);	
		}	
}