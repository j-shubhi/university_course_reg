package system;

import java.util.ArrayList;
import java.util.List;

public class Feedback<T> {
    private List<T> feedbackList;

    public Feedback() {
        this.feedbackList = new ArrayList<>();
    }

    public void addFeedback(T feedback) {
        feedbackList.add(feedback);
    }

    public List<T> getFeedbackList() {
        return feedbackList;
    }
}