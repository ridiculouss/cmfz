package com.baizhi;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.AddressBook;
import com.baizhi.entity.User;
import com.baizhi.util.Abook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.util.annotation.Nullable;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class CmfzPanApplicationTests {

    @Autowired
    private UserDao userDao;

    @Test
    public void contextLoads() throws Exception{
        List<User> list = userDao.selectAllUser(0, 8);
        List<AddressBook> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            AddressBook ab = new AddressBook();
            ab.setId(i+1+"");
            ab.setName(list.get(i).getDharmaName());
            ab.setCity(list.get(i).getCity());
            ab.setPhone(list.get(i).getPhone());
            ab.setCreateTime(list.get(i).getRegistTime());
            list1.add(ab);
        }
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("用户表");
        Row row = sheet.createRow(0);
        Class<AddressBook> addressBookClass = AddressBook.class;
        Field[] declaredFields = addressBookClass.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field declaredField = declaredFields[i];
            String name = declaredField.getAnnotation(Abook.class).name();
            row.createCell(i).setCellValue(name);
        }

        for (int i = 0; i < list1.size(); i++) {
            AddressBook addressBook = list1.get(i);
            Class<AddressBook> aClass = AddressBook.class;
            Field[] fields = aClass.getDeclaredFields();
            HSSFRow row1 = sheet.createRow(i + 1);
            for (int j = 0; j < fields.length - 1; j++) {
                String n = fields[j].getName();
                n = n.substring(0, 1).toUpperCase() + n.substring(1);
                Method declaredMethod = aClass.getDeclaredMethod("get" + n);
                row1.createCell(j).setCellValue(declaredMethod.invoke(addressBook).toString());
            }
            String n = fields[fields.length - 1].getName();
            n = n.substring(0, 1).toUpperCase() + n.substring(1);
            Method declaredMethod = aClass.getDeclaredMethod("get" + n);
            row1.createCell(fields.length - 1).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format((Date) declaredMethod.invoke(addressBook)));
        }

        workbook.write(new FileOutputStream("E:/testPOI/第一个Excel文档.xls"));
        workbook.close();
    }

    @Test
    public void importPOI() throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream("E:/testPOI/第一个Excel文档.xls"));
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        List<AddressBook> list = new ArrayList<>();
        for (int i = 0; i < lastRowNum; i++) {
            Row row = sheet.getRow(i + 1);
            int lastCellNum = row.getLastCellNum() - 1;

            AddressBook addressBook = new AddressBook();

            Class<AddressBook> aClass = AddressBook.class;
            Field[] fields = aClass.getDeclaredFields();

            for (int j = 0; j < lastCellNum; j++) {
                String name = fields[j].getName();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                Method declaredMethod = aClass.getDeclaredMethod(("set" + name), String.class);
                declaredMethod.invoke(addressBook, row.getCell(j).getStringCellValue());
            }
            String name = fields[lastCellNum].getName();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            Method declaredMethod = aClass.getDeclaredMethod(("set" + name), Date.class);
            declaredMethod.invoke(addressBook, new SimpleDateFormat("yyyy-MM-dd").parse(row.getCell(lastCellNum).getStringCellValue()));

            list.add(addressBook);
        }

        for (AddressBook addressBook : list) {
            System.out.println(addressBook);
        }
    }

    @Test
    public void aaa(){
        File file1 = new File("E:/tempPOI/");
        File file2 = new File(file1, "sdfsd.abc");
        String absolutePath = file2.getAbsolutePath();
        System.out.println(absolutePath);
    }
}