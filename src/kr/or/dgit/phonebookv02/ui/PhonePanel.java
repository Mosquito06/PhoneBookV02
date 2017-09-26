package kr.or.dgit.phonebookv02.ui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import kr.or.dgit.phonebookv02.dto.Phone;
import java.awt.FlowLayout;

public class PhonePanel extends JPanel {
	private JTextField tfName;
	private JTextField tfAddr;
	private JTextField tfTel;
	private JButton btn;

	public PhonePanel() {
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);

		JLabel lblName = new JLabel("성명");
		add(lblName);

		tfName = new JTextField();
		add(tfName);
		tfName.setColumns(10);

		JLabel lblAddr = new JLabel("주소");
		add(lblAddr);

		tfAddr = new JTextField();
		tfAddr.setColumns(10);
		add(tfAddr);

		JLabel lblTel = new JLabel("연락처");
		add(lblTel);

		tfTel = new JTextField();
		tfTel.setColumns(10);
		add(tfTel);

		btn = new JButton("추가");
		add(btn);

	}

	public JButton getBtn() {
		return btn;
	}
	
	public void setPhone(Phone phone) {
		tfName.setText(phone.getName());
		tfAddr.setText(phone.getAddr());
		tfTel.setText(phone.getTel());
	}

	public Phone getPhone() {
		String name = tfName.getText();
		String addr = tfAddr.getText();
		String tel = tfTel.getText();
		return new Phone(name, addr, tel);
	}

	public void clearTf() {
		tfName.setText("");
		tfAddr.setText("");
		tfTel.setText("");
	}

}
