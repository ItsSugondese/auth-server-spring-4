package com.lazy.authserver.mapper.user;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserDetailMapper {

//    @Select("select u.id as userId,u.email_address as userEmail from users u where u.is_active=true order by u.id asc")
//    List<UserBasicDetailResponsePojo> getAllUserIdName();
//
//
//    @Select("select u.id as id,u.email_address as email_address, u.full_name as full_name, u.mobile as mobile, \n" +
//            "u.account_non_locked as account_non_locked, u.user_type as user_type, u.description as description, u.profile_path as profile,\n" +
//            "(select string_agg(role_id::text, ',')  from users_role_mapping urm where urm.user_id=u.id) as role_id_string\n" +
//            "from users u where u.id= #{id}")
//    Optional<UserDetailResponsePojo> getUserIdName(Integer id);
//
//
//    @Select("select case when s.full_name is null then sd.full_name else s.full_name end \n" +
//            "as fullName,u.user_type as userType,u.id as id,u.email_address as userEmail from users u left join staff s on u.email_address=s.email left join \n" +
//            "student_details sd on u.email_address = sd.email\n" +
//            "where u.id=#{id}")
//    UserBasicDetailResponsePojo getFullNameOfUser(Integer id);

}
