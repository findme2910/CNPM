package view;

import javax.swing.*;

import decorator.DichVu;
import observer_singleton.BenhNhan;
import stragety_ThanhToan.Bill;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PaymentForm extends JFrame implements ActionListener {

	private BenhNhan benhNhan;
	private DichVu dichVu;
	private JLabel checkOutBillLabel;
	private JLabel patientIdLabel;
	private JTextField patientIdTextField;
	private JCheckBox generalCheck;
	private JCheckBox bloodTestCheck;
	private JCheckBox xRayCheck;
	private JCheckBox surgeryCheck;
	private JCheckBox med1Check;
	private JCheckBox med2Check;
	private JCheckBox med3Check;
	private JCheckBox med4Check;
	private JCheckBox med5Check;
	private JCheckBox med6Check;
	private JCheckBox med7Check;
	private JTextField insuranceTextField;
	private JRadioButton cashRadioButton;
	private JRadioButton transferRadioButton;
	private JButton printInvoiceButton;
	private JLabel totalAmountLabel;
	private JFrame invoiceFrame;

	public PaymentForm() {
		setTitle("Thanh toán hoá đơn");
		setSize(650, 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// Layout chính sử dụng BorderLayout
		JPanel mainPanel = new JPanel(new BorderLayout());

		// Panel trên sử dụng GridBagLayout
		JPanel topPanel = new JPanel(new GridBagLayout());
		JPanel titlePanel = new JPanel();
		JLabel titleLabel = new JLabel("Thanh toán hoá đơn cho bệnh nhân");
		titlePanel.add(titleLabel);

		mainPanel.add(titlePanel, BorderLayout.NORTH);

		// Label Mã bệnh nhân
		JPanel contentPanel = new JPanel(new BorderLayout());
		patientIdLabel = new JLabel("Mã bệnh nhân:");
		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx = 1.0;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = new Insets(5, 5, 5, 5);
		topPanel.add(patientIdLabel, gc);

		patientIdTextField = new JTextField(20);
		gc.gridx = 1;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.HORIZONTAL;
		topPanel.add(patientIdTextField, gc);

		// Các dịch vụ đã sử dụng
		JLabel servicesLabel = new JLabel("Các dịch vụ đã sử dụng:");
		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = 4;
		gc.anchor = GridBagConstraints.WEST;
		topPanel.add(servicesLabel, gc);

		generalCheck = new JCheckBox("Khám tổng quát");
		gc.gridx = 0;
		gc.gridy = 2;
		gc.gridwidth = 1;
		gc.anchor = GridBagConstraints.WEST;
		topPanel.add(generalCheck, gc);

		bloodTestCheck = new JCheckBox("Xét nghiệm máu");
		gc.gridx = 1;
		gc.gridy = 2;
		gc.gridwidth = 1;
		gc.anchor = GridBagConstraints.WEST;
		topPanel.add(bloodTestCheck, gc);

		xRayCheck = new JCheckBox("Chụp XQuang");
		gc.gridx = 2;
		gc.gridy = 2;
		gc.gridwidth = 1;
		gc.anchor = GridBagConstraints.WEST;
		topPanel.add(xRayCheck, gc);

		surgeryCheck = new JCheckBox("Phẫu thuật");
		gc.gridx = 3;
		gc.gridy = 2;
		gc.gridwidth = 1;
		gc.anchor = GridBagConstraints.WEST;
		topPanel.add(surgeryCheck, gc);

		// Thuốc
		JLabel medicationLabel = new JLabel("Thuốc:");
		gc.gridx = 0;
		gc.gridy = 4;
		gc.gridwidth = 2;
		gc.anchor = GridBagConstraints.CENTER;
		topPanel.add(medicationLabel, gc);

		med1Check = new JCheckBox("Thuốc cảm cúm");
		gc.gridx = 1;
		gc.gridy = 4;
		gc.gridwidth = 1;
		gc.anchor = GridBagConstraints.WEST;
		topPanel.add(med1Check, gc);

		med2Check = new JCheckBox("Thuốc chóng mặt");
		gc.gridx = 2;
		gc.gridy = 4;
		gc.anchor = GridBagConstraints.WEST;
		topPanel.add(med2Check, gc);

		med3Check = new JCheckBox("Thuốc đau bụng");
		gc.gridx = 3;
		gc.gridy = 4;
		gc.anchor = GridBagConstraints.WEST;
		topPanel.add(med3Check, gc);

		med4Check = new JCheckBox("Thuốc giảm đau");
		gc.gridx = 4;
		gc.gridy = 4;
		gc.anchor = GridBagConstraints.WEST;
		topPanel.add(med4Check, gc);

		med5Check = new JCheckBox("Thuốc ho");
		gc.gridx = 1;
		gc.gridy = 5;
		gc.anchor = GridBagConstraints.WEST;
		topPanel.add(med5Check, gc);

		med6Check = new JCheckBox("Thuốc ngủ");
		gc.gridx = 2;
		gc.gridy = 5;
		gc.anchor = GridBagConstraints.WEST;
		topPanel.add(med6Check, gc);
		med7Check = new JCheckBox("Thuốc sổ mũi");
		gc.gridx = 3;
		gc.gridy = 5;
		gc.anchor = GridBagConstraints.WEST;
		topPanel.add(med7Check, gc);

		contentPanel.add(topPanel, BorderLayout.CENTER);

		// Panel dưới sử dụng GridBagLayout
		JPanel bottomPanel = new JPanel(new GridBagLayout());

		// Thẻ bảo hiểm
		JLabel insuranceLabel = new JLabel("Thẻ bảo hiểm:");
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.WEST;
		bottomPanel.add(insuranceLabel, gc);

		insuranceTextField = new JTextField(20);
		gc.gridx = 1;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.HORIZONTAL;
		bottomPanel.add(insuranceTextField, gc);

		// Hình thức thanh toán
		JLabel paymentMethodLabel = new JLabel("Hình thức thanh toán:");
		gc.weightx = 1.0;
		gc.gridx = 0;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.WEST;
		bottomPanel.add(paymentMethodLabel, gc);

		ButtonGroup paymentMethodGroup = new ButtonGroup();
		cashRadioButton = new JRadioButton("Tiền mặt");
		gc.weightx = 1.0;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.WEST;
		bottomPanel.add(cashRadioButton, gc);
		paymentMethodGroup.add(cashRadioButton);

		transferRadioButton = new JRadioButton("Chuyển khoản");
		gc.weightx = 1.0;
		gc.gridx = 2;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.WEST;
		bottomPanel.add(transferRadioButton, gc);
		paymentMethodGroup.add(transferRadioButton);

		// Tổng tiền
		totalAmountLabel = new JLabel("Tổng tiền:");
		gc.gridx = 0;
		gc.gridy = 2;
		gc.anchor = GridBagConstraints.WEST;
		bottomPanel.add(totalAmountLabel, gc);

		JLabel totalAmountTextLabel = new JLabel("");
		gc.gridx = 1;
		gc.gridy = 2;
		gc.anchor = GridBagConstraints.WEST;
		bottomPanel.add(totalAmountTextLabel, gc);

		printInvoiceButton = new JButton("Xuất hoá đơn");
		gc.gridx = 2;
		gc.gridy = 2;
		gc.gridwidth = 3;
		gc.anchor = GridBagConstraints.WEST;
		bottomPanel.add(printInvoiceButton, gc);
		contentPanel.add(bottomPanel, BorderLayout.SOUTH);

		mainPanel.add(contentPanel, BorderLayout.CENTER);
		printInvoiceButton.addActionListener(this);

		add(mainPanel);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
	    if (e.getActionCommand().equals("Xuất hoá đơn")) {
	        String patientId = patientIdTextField.getText();
	        String insurance = insuranceTextField.getText();
	        boolean validData = true;

	        if (!isNumeric(patientId)) {
	            validData = false;
	            JOptionPane.showMessageDialog(null, "Mã bệnh nhân phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        }

	        if (!isNumeric(insurance) && !insurance.isEmpty()) {
	            validData = false;
	            JOptionPane.showMessageDialog(null, "Mã thẻ bảo hiểm phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        }

	        if (patientId.isEmpty()) {
	            validData = false;
	            JOptionPane.showMessageDialog(null, "Vui lòng điền mã bệnh nhân!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        }

	        boolean emptyInsurance = insurance.isEmpty();

	        if (validData) {
	        	if (validData) {
	                try {
	                    String fileName = "invoice.txt";
	                    FileWriter fileWriter = new FileWriter(fileName, true);
	                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	                    String invoice = "Mã bệnh nhân: " + patientId;
	                    if (!emptyInsurance) {
	                        invoice += ", Mã thẻ bảo hiểm: " + insurance + "\n";
	                    }
	                    bufferedWriter.write(invoice);

	                    double totalAmount = 0;

	                    if (xRayCheck.isSelected()) {
	                        totalAmount += 300000;
	                    }
	                    if (generalCheck.isSelected()) {
	                        totalAmount += 500000;
	                    }
	                    if (bloodTestCheck.isSelected()) {
	                        totalAmount += 200000;
	                    }
	                    if (surgeryCheck.isSelected()) {
	                        totalAmount += 1000000;
	                    }
	                    if (med1Check.isSelected()) {
	                        totalAmount += 5000;
	                    }
	                    if (med2Check.isSelected()) {
	                        totalAmount += 3000;
	                    }
	                    if (med3Check.isSelected()) {
	                        totalAmount += 20000;
	                    }
	                    if (med4Check.isSelected()) {
	                        totalAmount += 3000;
	                    }
	                    if (med5Check.isSelected()) {
	                        totalAmount += 2000;
	                    }
	                    if (med6Check.isSelected()) {
	                        totalAmount += 6000;
	                    }
	                    if (med7Check.isSelected()) {
	                        totalAmount += 2000;
	                    }

	                    if (!emptyInsurance) {
	                        double discountAmount = totalAmount * 0.5; // Tính số tiền bệnh nhân được giảm giá 50%
	                        totalAmount *= 0.5; // Giảm giá 50% khi có thẻ bảo hiểm
	                        invoice += "Số tiền giảm giá: " + discountAmount + " VND\n";
	                    }

	                    invoice += "Tổng tiền: " + totalAmount + " VND\n\n";

	                    bufferedWriter.write(invoice);
	                    bufferedWriter.close();
	                    System.out.println("Đã lưu hoá đơn vào file " + fileName);
	                } catch (IOException ex) {
	                    ex.printStackTrace();
	                }
	            }
	        }

	        double totalAmount = 0;

	        if (xRayCheck.isSelected()) {
	            totalAmount += 300000;
	        }
	        if (generalCheck.isSelected()) {
	            totalAmount += 500000;
	        }
	        if (bloodTestCheck.isSelected()) {
	            totalAmount += 200000;
	        }
	        if (surgeryCheck.isSelected()) {
	            totalAmount += 1000000;
	        }
	        if (med1Check.isSelected()) {
	            totalAmount += 5000;
	        }
	        if (med2Check.isSelected()) {
	            totalAmount += 3000;
	        }
	        if (med3Check.isSelected()) {
	            totalAmount += 20000;
	        }
	        if (med4Check.isSelected()) {
	            totalAmount += 3000;
	        }
	        if (med5Check.isSelected()) {
	            totalAmount += 2000;
	        }
	        if (med6Check.isSelected()) {
	            totalAmount += 6000;
	        }
	        if (med7Check.isSelected()) {
	            totalAmount += 2000;
	        }
	        

	        totalAmountLabel.setText(String.valueOf(totalAmount) + " VND");

	        if (e.getSource() == printInvoiceButton) {
	            if (!validData) {
	                JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	                return;
	            }
	            String invoice = "====================\n" + "   Hoá đơn thanh toán\n" + "====================\n"
	                    + "Mã bệnh nhân: " + patientId + "\n";
	            if (xRayCheck.isSelected()) {
	                invoice += "- Chụp X-Quang: 300,000 VND\n";
	            }
	            if (generalCheck.isSelected()) {
	                invoice += "- Khám tổng quát: 500,000 VND\n";
	            }
	            if (bloodTestCheck.isSelected()) {
	                invoice += "- Xét nghiệm máu: 200,000 VND\n";
	            }
	            if (surgeryCheck.isSelected()) {
	                invoice += "- Phẫu thuật: 1,000,000 VND\n";
	            }
	            if (med1Check.isSelected()) {
	                invoice += "- Thuốc cảm cúm: 5,000 VND\n";
	            }
	            if (med2Check.isSelected()) {
	                invoice += "- Thuốc chóng mặt: 3,000 VND\n";
	            }
	            if (med3Check.isSelected()) {
	                invoice += "- Thuốc đau bụng: 20,000 VND\n";
	            }
	            if (med4Check.isSelected()) {
	                invoice += "- Thuốc giảm đau: 3,000 VND\n";
	            }
	            if (med5Check.isSelected()) {
	                invoice += "- Thuốc ho: 2,000 VND\n";
	            }
	            if (med6Check.isSelected()) {
	                invoice += "- Thuốc ngủ: 6,000 VND\n";
	            }
	            if (med7Check.isSelected()) {
	                invoice += "- Thuốc sổ mũi: 2,000 VND\n";
	            }
	            if (!emptyInsurance) {
		            double discountAmount = totalAmount * 0.5; // Tính số tiền bệnh nhân được giảm giá 50%
		            totalAmount *= 0.5; // Giảm giá 50% khi có thẻ bảo hiểm
		            invoice += "- Giảm giá thẻ bảo hiểm: " + discountAmount + "VND\n";
		        }

	            invoice += "\nTổng tiền: " + totalAmount + " VND";

	            patientIdTextField.setText("");
	            generalCheck.setSelected(false);
	            bloodTestCheck.setSelected(false);
	            xRayCheck.setSelected(false);
	            surgeryCheck.setSelected(false);
	            med1Check.setSelected(false);
	            med2Check.setSelected(false);
	            med3Check.setSelected(false);
	            med4Check.setSelected(false);
	            med5Check.setSelected(false);
	            med6Check.setSelected(false);
	            med7Check.setSelected(false);
	            insuranceTextField.setText("");
	            cashRadioButton.setSelected(true);
	            totalAmountLabel.setText("Tổng tiền:");

	            JFrame invoiceFrame = new JFrame();
	            JTextArea invoiceTextArea = new JTextArea(invoice);
	            invoiceTextArea.setEditable(false);
	            JScrollPane scrollPane = new JScrollPane(invoiceTextArea);
	            invoiceFrame.add(scrollPane);
	            invoiceFrame.setTitle("Hoá đơn thanh toán");
	            invoiceFrame.setSize(400, 300);
	            invoiceFrame.setLocationRelativeTo(null);
	            invoiceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	            invoiceFrame.setVisible(true);
	        }
	    }
	}

	private boolean isNumeric(String str) {
	    if (str == null || str.length() == 0) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(str);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}



		public static void main(String[] args) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new PaymentForm();
				}
			});
		}
}
