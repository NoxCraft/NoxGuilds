package com.noxpvp.noxguilds.internal;

import java.util.Iterator;
import java.util.List;

import mkremins.fanciful.FancyMessage;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.noxpvp.noxguilds.chat.FanceeMessage;

public class Question {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private final FancyMessage			question;
	private final List<QuestionOption>	options;
	private final CommandSender[]		recievers;
	
	// private final int timeOutWait;
	// private long startTimeStamp;
	// private boolean finished;
	// private NoxListener<NoxGuilds> commandListener;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public Question(FancyMessage question, List<QuestionOption> options,
			CommandSender... receivers) {
		this(question, options, 15, receivers);
	}
	
	public Question(FancyMessage question, List<QuestionOption> options,
			int secondTimeout,
			CommandSender... receivers) {
		this.question = question;
		this.options = options;
		recievers = receivers;
		// timeOutWait = secondTimeout;
		
		// commandListener = new
		// NoxListener<NoxGuilds>(NoxGuilds.getInstance()) {
		//
		// @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled =
		// true)
		// public void onCommand(CommandEvent event) {
		// if (isExpired()) {
		// unregister();
		// return;
		// }
		//
		// if (!(event.getSender() instanceof Player))
		// return;
		//
		// if (event.getArgs().length > 0)
		// return;
		//
		// for (final QuestionOption o : Question.this.options) {
		// if (!event.getLabel().equalsIgnoreCase(
		// o.getUniqueCommand())) {
		// continue;
		// }
		//
		// o.onChoose((Player) event.getSender());
		// setFinished(true);
		// break;
		// }
		// }
		//
		// };
		
	}
	
	public Question(String message, List<QuestionOption> options,
			CommandSender... receivers) {
		this(new FancyMessage(message), options, 15, receivers);
	}
	
	public Question(String message, List<QuestionOption> options, int SecondTimeout,
			CommandSender... receivers) {
		this(new FancyMessage(message), options, SecondTimeout, receivers);
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// public boolean isExpired() {
	// return startTimeStamp > 0
	// && startTimeStamp + timeOutWait * 1000 < System.currentTimeMillis();
	// }
	//
	// public boolean isFinished() {
	// return finished;
	// }
	//
	// public abstract void onExpire();
	
	public void send() {
		// commandListener.register();
		
		question.then(" Options: ")
				.color(ChatColor.YELLOW);
		
		final Iterator<QuestionOption> optIter = options.iterator();
		while (optIter.hasNext()) {
			final QuestionOption o = optIter.next();
			
			question.then(o.getName())
					.command("/" + o.getCommand())
					.color(ChatColor.AQUA)
					.style(ChatColor.UNDERLINE);
			
			if (optIter.hasNext()) {
				question.then(", ")
						.color(ChatColor.YELLOW);
			}
		}
		
		new FanceeMessage(question).send(recievers);
		
		// Bukkit.getScheduler().runTaskLater(NoxGuilds.getInstance(), new
		// Runnable() {
		//
		// public void run() {
		// if (Question.this.isFinished())
		// return;
		//
		// Question.this.onExpire();
		// Question.this.setFinished(true);
		// }
		//
		// }, timeOutWait * 20);
		//
		// startTimeStamp = System.currentTimeMillis();
		
	}
	
	// public void setFinished(boolean finished) {
	// this.finished = finished;
	// }
	
}
