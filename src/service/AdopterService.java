
package service;

import models.Adopter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdopterService {

    private final List<Adopter> adopters = new ArrayList<>();

    public void addAdopter(Adopter adopter) {
        if (adopter == null) {
            throw new IllegalArgumentException(
                "Adopter cannot be null."
            );
        }

        for (Adopter existing : adopters) {
            if (existing.getUserId() == adopter.getUserId()) {
                throw new IllegalArgumentException(
                    "Duplicate adopter ID."
                );
            }

            if (existing.getEmail().equalsIgnoreCase(
                    adopter.getEmail())) {
                throw new IllegalArgumentException(
                    "An account with this email already exists."
                );
            }
        }

        adopters.add(adopter);
    }

    public List<Adopter> getAdopters() {
        return Collections.unmodifiableList(adopters);
    }

    public Adopter findById(int userId) {
        for (Adopter adopter : adopters) {
            if (adopter.getUserId() == userId) {
                return adopter;
            }
        }

        return null;
    }

    public int getNextUserId() {
        int nextId = 1;

        for (Adopter adopter : adopters) {
            nextId = Math.max(
                nextId, adopter.getUserId() + 1
            );
        }

        return nextId;
    }

    public void loadAdopters(List<Adopter> savedAdopters) {
        if (savedAdopters == null) {
            throw new IllegalArgumentException(
                "Adopter list cannot be null."
            );
        }

        List<Adopter> validated = new ArrayList<>();

        for (Adopter adopter : savedAdopters) {
            if (adopter == null) {
                throw new IllegalArgumentException(
                    "Saved adopter cannot be null."
                );
            }

            for (Adopter existing : validated) {
                if (existing.getUserId() == adopter.getUserId()
                        || existing.getEmail().equalsIgnoreCase(
                            adopter.getEmail())) {
                    throw new IllegalArgumentException(
                        "Duplicate adopter ID or email in saved data."
                    );
                }
            }

            validated.add(adopter);
        }

        adopters.clear();
        adopters.addAll(validated);
    }
}
