package fr.gouv.stopc.robertserver.ws.service;

import fr.gouv.stopc.robertserver.ws.vo.RegisterStrictVo;


public interface CaptchaStrictService {

	boolean verifyCaptcha(RegisterStrictVo registerVo);

}
