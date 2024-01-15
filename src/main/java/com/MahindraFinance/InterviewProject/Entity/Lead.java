package com.MahindraFinance.InterviewProject.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_Leads")
public class Lead {

    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long Id;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z]+$", message = "should have only alphabets in first name")
    @Column(nullable = false)
    private String firstName;

    @Pattern(regexp = "^[A-Za-z]+$", message = "should have only alphabets in middle name")
    private String middleName;

    @Pattern(regexp = "^[A-Za-z]+$", message = "Last Name must contain only alphabets")
    @Column(nullable = false)
    @NotBlank
    private String lastName;

    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Mobile Number must start with 6-9 and be 10 digits")
    @Column(nullable = false)
    @NotBlank
    private String phoneNumber;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    @Pattern(regexp = "^(Male|Female|Others)$", message = "Gender must be Male, Female, or Others")
    @NotBlank
    private String gender;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @NotNull
    private Date dateOfBirth ;

    // Constructors, getters, and setters omitted for brevity
}
