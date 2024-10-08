package com.feirui.authorization.service.strategy;

import com.feirui.common.constant.AuthTypeConstant;
import com.feirui.authorization.domain.dto.AuthCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum AuthStrategyEnum {
    USER_POWER_ALL(AuthTypeConstant.USER_POWER_ALL) {
        @Override
        public void authExecute(AuthCondition authCondition) {

        }
    },
    USER_POWER_ALL_COMPANY_SON(AuthTypeConstant.USER_POWER_ALL_COMPANY_SON) {
        @Override
        public void authExecute(AuthCondition authCondition) {

        }
    },
    USER_POWER_COMPANY(AuthTypeConstant.USER_POWER_COMPANY) {
        @Override
        public void authExecute(AuthCondition authCondition) {

        }
    },
    USER_POWER_ALL_SON(AuthTypeConstant.USER_POWER_ALL_SON) {
        @Override
        public void authExecute(AuthCondition authCondition) {

        }
    },
    USER_POWER_DEPART(AuthTypeConstant.USER_POWER_DEPART) {
        @Override
        public void authExecute(AuthCondition authCondition) {

        }
    },
    USER_POWER_OWN(AuthTypeConstant.USER_POWER_OWN) {
        @Override
        public void authExecute(AuthCondition authCondition) {

        }
    },
    DEFINITION_DEPART(AuthTypeConstant.USER_DEFINITION_DEPART) {
        @Override
        public void authExecute(AuthCondition authCondition) {

        }
    },

    NO_AUTH(AuthTypeConstant.NO_AUTH) {
        @Override
        public void authExecute(AuthCondition authCondition) {

        }
    };

    private final String authType;

    public static AuthStrategyEnum getAuthStrategy(String authType) {
        if (StringUtils.hasLength(authType)) return NO_AUTH;
        for (AuthStrategyEnum authStrategy : AuthStrategyEnum.values()) {
            if (Objects.equals(authStrategy.authType, authType)) {
                return authStrategy;
            }
        }
        return NO_AUTH;
    }

    public abstract void authExecute(AuthCondition authCondition);
}
