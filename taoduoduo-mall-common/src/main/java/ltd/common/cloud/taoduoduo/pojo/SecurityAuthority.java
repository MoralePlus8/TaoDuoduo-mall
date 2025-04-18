package ltd.common.cloud.taoduoduo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityAuthority implements GrantedAuthority {



    private String authority;

}
