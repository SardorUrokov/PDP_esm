package com.example.pdp_esm.entity;

import com.example.pdp_esm.entity.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(unique = true)
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Enumerated(EnumType.STRING)
    private Roles roles = Roles.USER;

    private String fullName, phoneNumber, password;
    private boolean active = true;
    private String gender;

    //bot uchun
//    private String chatId;

    //tizim fordalanuvchilarini role lar ro'yxati
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
//        for (Role role : this.roles) {
//            grantedAuthorityList.add(new SimpleGrantedAuthority(role.getName()));
//        }
//        return grantedAuthorityList;
//    }
//
//    @Override
//    public String getPassword() {
//        return this.password;
//    }
//
//    //unique bo'ladigan ixtiyoriy field
//    @Override
//    public String getUsername() {
//        return this.phoneNumber;
//    }
//
//    private boolean accountNonExpired = true; //accountni vaqti o'tmaganmi?
//    private boolean accountNonLocked = true; //account bloklanmaganmi?
//    private boolean credentialsNonExpired = true; //parol o'znikimi?
//    private boolean enabled = true; //tizimga kimdir kirganda undan foydalanish mumkinmi?
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return this.accountNonExpired;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return this.accountNonLocked;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return this.credentialsNonExpired;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return this.enabled;
//    }

    public User(String fullName, String phoneNumber, String email, String password, String  gender, Roles roles, boolean active) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.roles = roles;
        this.active = active;
    }

    public User(String fullName, String phoneNumber, String email, String password, String  gender, boolean active) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.active = active;
    }
}