An example bot written in JavaScript is:

```
((time_left, state) => ({
    command: {
        action: "turn",
        metadata: {
            direction: "right"
        }
    },
    state: {
        hello: "world"
    }
});
```