# AchievementDTO


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**challengeId** | **number** |  | [default to undefined]
**name** | **string** |  | [optional] [default to undefined]
**description** | **string** |  | [optional] [default to undefined]
**shortDescription** | **string** |  | [optional] [default to undefined]
**state** | [**State**](State.md) |  | [optional] [default to undefined]
**achievementThreshHolds** | [**Array&lt;AchievementThresholdDTO&gt;**](AchievementThresholdDTO.md) |  | [optional] [default to undefined]

## Example

```typescript
import { AchievementDTO } from './api';

const instance: AchievementDTO = {
    challengeId,
    name,
    description,
    shortDescription,
    state,
    achievementThreshHolds,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
