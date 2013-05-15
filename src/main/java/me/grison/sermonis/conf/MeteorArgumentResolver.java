package me.grison.sermonis.conf;

import org.atmosphere.cpr.Meteor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * Meteor Argument Resolver.
 */
public class MeteorArgumentResolver implements HandlerMethodArgumentResolver {
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Meteor.class);
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, //
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return Meteor.build(webRequest.getNativeRequest(HttpServletRequest.class));
    }
}