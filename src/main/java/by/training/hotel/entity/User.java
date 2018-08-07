package by.training.hotel.entity;

import org.joda.time.LocalDate;

import java.util.Objects;

public class User implements Entity {

    private static final long serialVersionUID = -3764600327971412008L;

    private Integer userId;

    private String email;

    private String password;

    private String name;

    private String surname;

    private String telNumber;

    private LocalDate birthday;

    private Integer discount;

    private Boolean genderMale;

    private Boolean blocked;

    private UserRole role;

    public User() { }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Boolean getGenderMale() {
        return genderMale;
    }

    public void setGenderMale(Boolean genderMale) {
        this.genderMale = genderMale;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(getUserId(), user.getUserId()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getSurname(), user.getSurname()) &&
                Objects.equals(getTelNumber(), user.getTelNumber()) &&
                Objects.equals(getBirthday(), user.getBirthday()) &&
                Objects.equals(getDiscount(), user.getDiscount()) &&
                Objects.equals(getGenderMale(), user.getGenderMale()) &&
                Objects.equals(getBlocked(), user.getBlocked()) &&
                getRole() == user.getRole();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUserId(), getEmail(), getPassword(), getName(), getSurname(), getTelNumber(), getBirthday(), getDiscount(), getGenderMale(), getBlocked(), getRole());
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", telNumber='" + telNumber + '\'' +
                ", birthday=" + birthday +
                ", discount=" + discount +
                ", genderMale=" + genderMale +
                ", blocked=" + blocked +
                ", role=" + role +
                '}';
    }
}