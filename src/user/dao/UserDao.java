package user.dao;

public interface UserDao {

    boolean login(String u_username, String u_password);

    int re(String u_username,String u_password,String u_sex,String u_hobbies,String
           u_phone,String u_email,String u_address,String is_delete);
}
