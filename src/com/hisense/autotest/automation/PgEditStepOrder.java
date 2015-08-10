package com.hisense.autotest.automation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;


public class PgEditStepOrder extends Composite{

    public PgEditStepOrder(Composite parent, int style) {
        super(parent,style);
    }

    private Menu menu;
    
    public void editStep(final Table tblMScript,Point pt){
        menu = new Menu(tblMScript);
        final int selIndex = tblMScript.getSelectionIndex();
        if (null == tblMScript.getItem(pt)) {
            return;
        }
        MenuItem menuItem_up = new MenuItem(menu, SWT.PUSH);
        menuItem_up.setText("上移");
        MenuItem menuItem_down = new MenuItem(menu, SWT.PUSH);
        menuItem_down.setText("下移");
        MenuItem menuItem_top = new MenuItem(menu, SWT.PUSH);
        menuItem_top.setText("置顶");
        MenuItem menuItem_bottm = new MenuItem(menu, SWT.PUSH);
        menuItem_bottm.setText("置底");
        // 第一个Item无法上移和置顶
        if (tblMScript.getItem(pt).equals(tblMScript.getItem(0))) {
            menuItem_up.setEnabled(false);
            menuItem_top.setEnabled(false);
        }
        //最后一个Item无法下移和置底
        if (tblMScript.getItem(pt).equals(tblMScript.getItem(tblMScript.getItemCount()-1))) {
            menuItem_down.setEnabled(false);
            menuItem_bottm.setEnabled(false);
        }
        menuItem_up.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event arg0) {
                int colCnt = tblMScript.getColumnCount();
                //上移
                exchange(tblMScript.getItem(selIndex),
                        tblMScript.getItem(selIndex - 1), colCnt);
            }
        });
        menuItem_top.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event arg0) {
                int colCnt = tblMScript.getColumnCount();
                // 置顶
                for (int i = selIndex; i > 0; i--) {
                    exchange(tblMScript.getItem(i), tblMScript.getItem(i - 1),
                            colCnt);
                }
            }
        });
        menuItem_bottm.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event arg0) {
                int colCnt = tblMScript.getColumnCount();
                // 置底
                for (int i = selIndex; i < tblMScript.getItemCount() - 1; i++) {
                    exchange(tblMScript.getItem(i), tblMScript.getItem(i + 1),
                            colCnt);
                }
            }
        });
        menuItem_down.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event arg0) {
                int colCnt = tblMScript.getColumnCount();
                // 下移
                exchange(tblMScript.getItem(selIndex),
                        tblMScript.getItem(selIndex + 1), colCnt);
            }
        });
        menu.setVisible(true);
    }

    /**
     * 交换Item内容
     * 
     * @param firstItem
     * @param secondItem
     * @param colCnt
     *            table的列数
     */
    private void exchange(TableItem firstItem, TableItem secondItem, int colCnt) {
        String firstItemVals = "";
        String secondItemVals = "";
        for (int i = 1; i < colCnt; i++) {
            firstItemVals = firstItem.getText(i);
            secondItemVals = secondItem.getText(i);
            firstItem.setText(i, secondItemVals);
            secondItem.setText(i, firstItemVals);
        }
    }

}
