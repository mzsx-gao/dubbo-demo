package gao.soa.dubbo.spring.parse;

import gao.soa.dubbo.spring.configBean.Protocol;
import gao.soa.dubbo.spring.configBean.Reference;
import gao.soa.dubbo.spring.configBean.Registry;
import gao.soa.dubbo.spring.configBean.Service;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


public class SOANamespaceHandler extends NamespaceHandlerSupport {

    public void init() {
        this.registerBeanDefinitionParser("registry", new RegistryBeanDefinitionParse(Registry.class));
        this.registerBeanDefinitionParser("reference", new ReferenceBeanDefinitionParse(Reference.class));
        this.registerBeanDefinitionParser("protocol", new ProtocolBeanDefinitionParse(Protocol.class));
        this.registerBeanDefinitionParser("service", new ServiceBeanDefinitionParse(Service.class));
    }
}
