exports.handler = (event, context) => {
    // This is used to know how much time you have left for your code
    const { getRemainingTimeInMillis: time_left } = context;

    const wombatFn = eval(event.code);
    const result = wombatFn(time_left, event.state);

    const { succeed } = context;
    succeed(result);
};