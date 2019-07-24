package com.baizhi.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.util.MD5Utils;
import com.baizhi.util.Page;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<User> queryAllUser(Map<String, Object> map) {
        Integer rowNum = Integer.valueOf(map.get("rowNum").toString());
        Integer currentPage = Integer.valueOf(map.get("page").toString());
        Page<User> page = new Page<>();
        page.setPageSize(rowNum);
        page.setCurrentPage(currentPage);
        List<User> users = userDao.selectAllUser(page.getBegin(), page.getPageSize());
        int count = userDao.selectAllCount();
        page.setResult(users);
        page.setTotal(count);
        return page;
    }

    @Override
    public String addUser(User user) {
        String id = UUID.randomUUID().toString();
        user.setId(id);
        String salt = MD5Utils.getSalt();
        user.setSalt(salt);
        String password = MD5Utils.getPassword(user + salt);
        user.setPassword(password);
        int i = userDao.insertUser(user);
        if (i != 0) return id;
        return null;
    }

    @Override
    public String modifyUser(User user, String opassword) {
        String s = null;
        if (!opassword.equals(user.getPassword())){
            String salt = MD5Utils.getSalt();
            user.setSalt(salt);
            String password = MD5Utils.getPassword(user + salt);
            user.setPassword(password);
        }
        int i = userDao.updateUser(user);
        if (i != 0) s = user.getId();
        if (user.getProfile().isEmpty()) s = null;
        return s;
    }

    @Override
    public void removeUser(String[] ids) {
        userDao.deleteUser(ids);
    }

    @Override
    public void modifyProfile(String id, String profileName) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("value", profileName);
        userDao.updateWithProfile(map);
    }

    @Override
    public String modifyStatus(String id, String status) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        if ("on".equals(status)) status = "off";
        else if ("off".equals(status)) status = "on";
        map.put("value", status);
        int i = userDao.updateWithStatus(map);
        if (i != 0) return "true";
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String exportUser(String filename, String realPath) {
        List<User> list = userDao.selectAllUser(0, 8);
        for (User user : list) {
            user.setProfile(realPath + user.getProfile());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息", "用户表"), User.class, list);
        String name = new Date().getTime()+"";
        try {
            workbook.write(new FileOutputStream(filename + "/" + name + ".xls"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

    @Override
    public void importUser(String absolutePath) throws Exception{
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(absolutePath));
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum() - 1;
        List<User> list = new ArrayList<>();
        for (int i = 0; i < lastRowNum; i++) {
            Row row = sheet.getRow(i + 2);
            int lastCellNum = row.getLastCellNum() - 1;

            User user = new User();

            Class<User> aClass = User.class;
            Field[] fields = aClass.getDeclaredFields();

            for (int j = 0; j < lastCellNum; j++) {
                String name = fields[j].getName();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                Method declaredMethod = aClass.getDeclaredMethod(("set" + name), String.class);
                declaredMethod.invoke(user, row.getCell(j).getStringCellValue());
            }
            String name = fields[lastCellNum].getName();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            Method declaredMethod = aClass.getDeclaredMethod(("set" + name), Date.class);
            declaredMethod.invoke(user, new SimpleDateFormat("yyyy-MM-dd").parse(row.getCell(lastCellNum).getStringCellValue()));

            list.add(user);
        }

        for (User user : list) {
            user.setId(UUID.randomUUID().toString());
            userDao.insertUser(user);
        }
    }
}
