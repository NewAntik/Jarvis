package ua.jarvis.strategy;

public interface ExecutorStrategy <T> {

	boolean isExecutorInstance(String text);

	T getExecutor();
}
