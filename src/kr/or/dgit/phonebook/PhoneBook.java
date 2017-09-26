package kr.or.dgit.phonebook;

import java.awt.EventQueue;

import kr.or.dgit.phonebookv02.ui.PhoneBookMainUI;

public class PhoneBook {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PhoneBookMainUI frame = new PhoneBookMainUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
