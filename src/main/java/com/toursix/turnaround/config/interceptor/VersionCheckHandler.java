package com.toursix.turnaround.config.interceptor;

import com.toursix.turnaround.common.exception.ErrorCode;
import com.toursix.turnaround.common.exception.NotFoundException;
import com.toursix.turnaround.common.exception.UpgradeRequiredException;
import com.toursix.turnaround.common.exception.ValidationException;
import com.toursix.turnaround.domain.deploy.Deploy;
import com.toursix.turnaround.domain.deploy.repository.DeployRepository;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class VersionCheckHandler {

    private final DeployRepository deployRepository;

    public void checkVersion(HttpServletRequest request) {
        String requestOs = request.getHeader("TurnaroundOsType");
        String requestVersion = request.getHeader("TurnaroundVersion");
        validateRequestOs(requestOs);
        validateRequestVersion(requestVersion);
        Deploy deploy = deployRepository.findDeployByOs(requestOs);
        if (deploy == null) {
            throw new NotFoundException(String.format("배포되지 않은 OS (%s) 입니다.", requestOs),
                    ErrorCode.NOT_FOUND_OS_EXCEPTION);
        }
        String latestVersion = deploy.getVersion();
        if (isOutdated(requestVersion, latestVersion)) {
            throw new UpgradeRequiredException(
                    String.format("업그레이드가 필요한 버전 (%s - %s) 입니다.", requestOs, requestVersion));
        }
    }

    private void validateRequestOs(String requestOs) {
        if (!requestOs.matches("iOS|AOS")) {
            throw new ValidationException(String.format("잘못된 OS 타입 요청 (%s) 입니다.", requestOs),
                    ErrorCode.VALIDATION_OS_EXCEPTION);
        }
    }

    private void validateRequestVersion(String requestVersion) {
        if (!requestVersion.matches("\\d+\\.\\d+\\.\\d+")) {
            throw new ValidationException(String.format("잘못된 버전 형식 (%s) 입니다.", requestVersion),
                    ErrorCode.VALIDATION_VERSION_EXCEPTION);
        }
    }

    private boolean isOutdated(String requestVersion, String latestVersion) {
        String[] request = requestVersion.split("\\.");
        String[] latest = latestVersion.split("\\.");
        if (Integer.parseInt(request[0]) < Integer.parseInt(latest[0])) {
            return true;
        }
        if (Integer.parseInt(request[1]) < Integer.parseInt(latest[1])) {
            return true;
        }
        return false;
    }
}
