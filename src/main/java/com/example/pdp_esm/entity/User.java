package com.example.pdp_esm.entity;

import com.example.pdp_esm.entity.template.AbsEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User extends AbsEntity
//        implements UserDetails
{

    @ManyToOne
    private Position position;

    @Column(unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    private String fullName, phoneNumber, password, code;
    private boolean active = true;

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

    public User(String fullName, String phone, String email, String password, Set<Role> roles, boolean enabled) {
        this.fullName = fullName;
        this.phoneNumber = phone;
        this.email = email;
        this.password = password;
        this.roles = roles;
//        this.enabled = enabled;
    }
}