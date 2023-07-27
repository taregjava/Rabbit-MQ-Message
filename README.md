# Rabbit-MQ-Message

## Example publishMultiple message post method http://localhost:8080/publishMultiple

```json
[
  {
    "messageId": 0,
    "message": "Message 1",
    "messageDate": "2023-07-27T12:00:00"
  },
  {
    "messageId": 0,
    "message": "Message 2",
    "messageDate": "2023-07-27T12:10:00"
  },
  {
    "messageId": 0,
    "message": "Message 3",
    "messageDate": "2023-07-27T12:20:00"
  }
]
```
### Endpoint
POST http://localhost:8080/publish

### Request Body
```json
{
  "messageId": 0,
  "message": "This is a test message",
  "messageDate": "2023-07-27T12:00:00"
}
```