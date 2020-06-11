package fr.gouv.stopc.robertserver.ws.vo;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class RegisterStrictVo extends RegisterVo{
  @JsonProperty(required = true)
  @NotNull
  @ToString.Exclude
  private String captchaId;
  
}
