package com.gui.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gui.blog.dao.mapper.SysUserMapper;
import com.gui.blog.dao.pojo.SysUser;
import com.gui.blog.service.LoginService;
import com.gui.blog.service.SysUserService;
import com.gui.blog.vo.ErrorCode;
import com.gui.blog.vo.LoginUserVo;
import com.gui.blog.vo.Result;
import com.gui.blog.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {
   @Autowired
   private SysUserMapper sysUserMapper;

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
   private LoginService loginService;

    @Override
    public SysUser finUserById(Long id) {
          SysUser sysUser = sysUserMapper.selectById(id);
          if(sysUser == null){
                sysUser = new SysUser();
                sysUser.setNickname("小猪猪");
          }
     return sysUser;
    }

    @Override
    public SysUser finUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        queryWrapper.select(SysUser::getId,SysUser::getNickname,SysUser::getAccount,SysUser::getAvatar);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result finUserByToken(String token) {
        /**
         * 1.token合法性校验
         *      是否为空，解析是否成功，redis是否存在
         * 2.校验失败 返回错误
         * 3.校验成功 返回用户信息 LoginUserVo
         */
        SysUser sysUser = loginService.checkToken(token);
        if(sysUser == null){
      return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(String.valueOf(sysUser.getId()));
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setAccount(sysUser.getAccount());
        return Result.success(loginUserVo);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void save(SysUser sysUser) {
        sysUserMapper.insert(sysUser);
    }

    @Override
    public UserVo findUserVoById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if(sysUser == null){
            sysUser = new SysUser();
            sysUser.setNickname("小猪猪");
        }
        UserVo userVo = new UserVo();
        userVo.setId(String.valueOf(sysUser.getId()));
        userVo.setNickname(sysUser.getNickname());
        userVo.setAvatar(sysUser.getAvatar());
        
        return userVo;
    }
}
