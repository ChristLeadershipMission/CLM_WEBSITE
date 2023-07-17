package worldwide.clm.clmwebsite.data.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import worldwide.clm.clmwebsite.enums.Role;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullName;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address address;
    @Column(unique = true)
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDateTime dateOfBirth;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Department department;
    private String levelOfEducation;
    private String occupation;
    private String academicHonour;
    private String maritalStatus;
    private String profilePicture;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Campus campus;
    @CreatedDate
    private LocalDateTime createdAt;
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    private List<Role> roles;
}
