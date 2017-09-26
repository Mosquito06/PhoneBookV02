package kr.or.dgit.phonebook.ctrl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit.InsertBreakAction;

import kr.or.dgit.phonebookv02.dto.Phone;

public class PhoneControl {
	private Map<String, Phone> phoneBook;

	public Map<String, Phone> getPhoneBook() {
		return phoneBook;
	}

	public PhoneControl() {
		phoneBook = new HashMap<>();
	}

	public void setPhoneBook(Map<String, Phone> phoneBook) {
		this.phoneBook = phoneBook;
	}

	@Override
	public String toString() {
		return String.format("%s", phoneBook);
	}

	public boolean insertPhone(Phone newPhone) {
		if (isExist(newPhone)) {
			phoneBook.remove(newPhone.getName());
		}
		phoneBook.put(newPhone.getName(), newPhone);
		return true;
	}

	public boolean isExist(Phone newPhone) {
		return phoneBook.containsKey(newPhone.getName());
	}

	public void deletePhone(Phone delphone) {
		phoneBook.remove(delphone.getName());
		JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다.");

	}

	public Phone searchPhone(Phone searchPhone) {
		if (!isExist(searchPhone)) {
			return null;
		} else {
			return phoneBook.get(searchPhone.getName());
		}

	}

	public Object[][] showPhones() {
		Object[][] datas = new Object[phoneBook.size()][];

		int i = 0;
		for (Entry<String, Phone> e : phoneBook.entrySet()) {
			Object[] arr = new Object[4]; 
			arr[0] = i + 1; 

			Object[] phoneArr = e.getValue().toArray();
			System.arraycopy(phoneArr, 0, arr, 1, phoneArr.length);
			datas[i] = arr;
			i++;
		}
		return datas;

	}

	public boolean saveData() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Java 파일", "java");
		chooser.setFileFilter(filter);

		int ret = chooser.showSaveDialog(null);
		if (ret != JFileChooser.APPROVE_OPTION) {
			return false;
		}

		String fileName = chooser.getSelectedFile().getPath() + ".java";
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
			oos.writeObject(phoneBook);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;

	}

	public boolean loadData() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Java", "java");
		chooser.addChoosableFileFilter(filter);

		int ret = chooser.showOpenDialog(null);
		if (ret != JFileChooser.APPROVE_OPTION) {
			return false;
		}
		String fileName = chooser.getSelectedFile().getPath();

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
			Map<String, Phone> data = (Map<String, Phone>) ois.readObject();
			this.setPhoneBook(data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}return true;
	}

}
