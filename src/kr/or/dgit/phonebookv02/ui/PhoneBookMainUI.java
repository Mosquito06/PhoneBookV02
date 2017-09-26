package kr.or.dgit.phonebookv02.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import kr.or.dgit.phonebook.ctrl.PhoneControl;
import kr.or.dgit.phonebookv02.dto.Phone;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JSeparator;

public class PhoneBookMainUI extends JFrame {
	public static final String[] COL_NAMES = { "번호", "이름", "주소", "연락처" };

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private PhoneControl phonecontrol;
	private JMenuBar menuBar;
	private JMenuItem mitemSave;
	private JMenu mnNewMenu;
	private JMenuItem mitemLoad;
	private JSeparator separator;
	private JMenuItem mitemExit;

	public PhoneBookMainUI() {
		phonecontrol = new PhoneControl();

		setTitle("연락처 관리");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 566, 300);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnNewMenu = new JMenu("File");
		mnNewMenu.setMnemonic('F');
		menuBar.add(mnNewMenu);

		mitemSave = new JMenuItem("저장");
		mnNewMenu.add(mitemSave);
		mitemSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (phonecontrol.saveData()) {
					JOptionPane.showMessageDialog(null, "저장되었습니다");
				} else {
					JOptionPane.showMessageDialog(null, "저장되지 않았습니다.");
				}

			}
		});

		mitemLoad = new JMenuItem("불러오기");
		mnNewMenu.add(mitemLoad);
		
		mitemLoad.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(phonecontrol.loadData()){
					model.setDataVector(getDatas(), PhoneBookMainUI.COL_NAMES);
					JOptionPane.showMessageDialog(null, "불러오기가 완료되었습니다.");
				} else {
					JOptionPane.showMessageDialog(null, "불러오기가 취소되었습니다.");
				}
			}
		});

		separator = new JSeparator();
		mnNewMenu.add(separator);

		mitemExit = new JMenuItem("종료");
		mnNewMenu.add(mitemExit);
		mitemExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int Corfirm = JOptionPane.showConfirmDialog(null, "정말 종료하시겠습니까?", "종료 확인 창", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (Corfirm == JOptionPane.YES_OPTION) {
					setVisible(false);
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
			}
		});

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);

		PhonePanel pPhone = new PhonePanel();
		contentPane.add(pPhone, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		model = new DefaultTableModel(getDatas(), PhoneBookMainUI.COL_NAMES);
		table = new JTable(model);

		scrollPane.setViewportView(table);

		JPopupMenu PopMenu = new JPopupMenu();
		JMenuItem delPopMenu = new JMenuItem("삭제");
		PopMenu.add(delPopMenu);
		delPopMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = (String) table.getValueAt(table.getSelectedRow(), 1);
				model.removeRow(table.getSelectedRow());
				phonecontrol.deletePhone(new Phone(name));
				model.setDataVector(getDatas(), PhoneBookMainUI.COL_NAMES);
			}
		});

		JMenuItem modPopmenu = new JMenuItem("수정");
		PopMenu.add(modPopmenu);
		modPopmenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pPhone.getBtn().setText("수정");
				String name = (String) table.getValueAt(table.getSelectedRow(), 1);
				String addr = (String) table.getValueAt(table.getSelectedRow(), 2);
				String tel = (String) table.getValueAt(table.getSelectedRow(), 3);
				Phone phone = new Phone(name, addr, tel);
				pPhone.setPhone(phone);

			}
		});

		pPhone.getBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (pPhone.getBtn().getText().equals("수정")) {
					Phone phone = pPhone.getPhone();
					getInsert(phone, "수정이 완료되었습니다");
				} else if (pPhone.getBtn().getText().equals("추가")) {
					Phone phone = pPhone.getPhone();
					getInsert(phone, "추가 되었습니다");
				}
				pPhone.clearTf();
				pPhone.getBtn().setText("추가");
			}

		});

		table.setComponentPopupMenu(PopMenu);

	}

	public void getInsert(Phone phone, String text) {
		if (phonecontrol.insertPhone(phone)) {
			JOptionPane.showMessageDialog(null, text);
			model.setDataVector(getDatas(), PhoneBookMainUI.COL_NAMES);

		}
	}

	private Object[][] getDatas() {
		return phonecontrol.showPhones();
	}

}
