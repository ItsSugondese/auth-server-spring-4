package com.lazy.authserver.generic.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Santosh Paudel
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActiveToggle {
    private long id;
    private boolean status;
}
