package com.hcb.util;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.hcb.factory.FactoryList;
import com.hcb.mc.bean.MutliBean;
import com.hcb.mc.bean.Player;
import com.hcb.mc.bean.PlayerList;

@Service
public class BeanUtil implements ApplicationContextAware {

	public static ApplicationContext applicationContext;
	
	@Resource
	private Player player;
	
	@Resource
	private PlayerList<Player> playerList;
	
	 @Resource
	 private FactoryList<MutliBean,String> beanInterfaceFactory;
	   
	 public void setApplicationContext(ApplicationContext argContext)
	            throws BeansException {
	        applicationContext = argContext;
	}
	 
    public FactoryList<MutliBean, String> getBeanInterfaceFactory() {
        return beanInterfaceFactory;
    }

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public PlayerList<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(PlayerList<Player> playerList) {
		this.playerList = playerList;
	}

}
