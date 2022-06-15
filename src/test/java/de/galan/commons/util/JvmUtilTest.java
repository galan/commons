package de.galan.commons.util;

import org.junit.jupiter.api.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import static org.assertj.core.api.Assertions.*;

public class JvmUtilTest {

    @Test
    public void pid(){
        RuntimeMXBean rmxb = ManagementFactory.getRuntimeMXBean();
        String pid = rmxb.getName().split("@")[0];
        assertThat(JvmUtil.getPid()).isEqualTo(pid);
    }

}
