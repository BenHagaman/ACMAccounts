package gui;

import db.JDBCConnection;
import test.TestObjects;
import vending.Controller;
import vending.Money;
import vending.Transaction;
import vending.UserSession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 12/2/12
 * Time: 4:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccountHistoryPanel extends PanelWithTitle {

    public AccountHistoryPanel() {
        super("Account History");

        Vector<String> headers= new Vector<String>();
        headers.add("Action");
        headers.add("Change in Balance");
        headers.add("New Balance");
        headers.add("Date");

        ResultSet rs = JDBCConnection.query("SELECT * FROM transactions WHERE card_number=\'"
                + UserSession.getCurrentSession().getUser().getCardno() + "\' ORDER BY trans_time DESC");

        Vector<Vector> data = new Vector<Vector>();

        try {
            while(rs.next()) {
                Vector<String> item = new Vector();

                item.add(rs.getString("description"));
                item.add(new Money(new Double(rs.getBigDecimal("amount").toString())).toString());
                item.add(new Money(new Double(rs.getBigDecimal("new_balance").toString())).toString());
                String date = new SimpleDateFormat("MMM dd, yyyy  hh:mm").format(rs.getTimestamp("trans_time"));
                //String date = SimpleDateFormat.getDateInstance(DateFormat.FULL).format(rs.getTimestamp("trans_time"));
                item.add(date);
                data.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        /*for(Transaction t: TestObjects.getTransactionList()) {
            Vector<String> item = new Vector();

            item.add(t.getPname());
            item.add(new Money(t.getAmount()).toString());
            item.add(new Money(t.getAcctBalance()).toString());
            item.add(t.getDate());
            data.add(item);
        }   */   //TODO

        JTable table = new JTable(new DefaultTableModel(data, headers) {
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        });

        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        table.setGridColor(new Color(215,215,215));
        table.setFont(new Font("Unused", Font.PLAIN, 14));
        table.setRowHeight(14 + 10);

        panel.setLayout(new GridLayout());

        JScrollPane jspane = new JScrollPane(table);
        //jspane.add(table);
        panel.add(BorderLayout.CENTER, jspane);
    }
}
