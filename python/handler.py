""" This is the Lambda handler for the Wombat's python environment. """

def handle_event(event, time_left):
    """ Handles the execution of a Wombat's code"""
    try:
        # The python function must be called "wombat"
        exec(event['code'])
        response = wombat(event['state'], time_left)

        return {
            'response': response,
            'error': None
        }

    except Exception as e:
        return {
            'response': None,
            'error': str(e)
        }

def lambda_handler(event, context):
    """ Entry point for Lambda function """
    time_left = context.get_remaining_time_in_millis
    return handle_event(event, time_left)
