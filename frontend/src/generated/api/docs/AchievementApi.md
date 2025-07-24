# AchievementApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**getAchievementDetailByChallengeId**](#getachievementdetailbychallengeid) | **GET** /api/achievements/{challengeId}/{language} | Get achievement detail by challenge id|
|[**getChallengeToken**](#getchallengetoken) | **GET** /api/achievements/token/{challengeId} | Get challenge token image by challenge id and tier|
|[**getPlayerAchievementByPUUID**](#getplayerachievementbypuuid) | **GET** /api/achievements/player/{pUUID}/{language} | Get all player achievements by pUUID|

# **getAchievementDetailByChallengeId**
> AchievementDTO getAchievementDetailByChallengeId()


### Example

```typescript
import {
    AchievementApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new AchievementApi(configuration);

let challengeId: string; //Player uuid (default to undefined)
let language: string; //local language (default to undefined)

const { status, data } = await apiInstance.getAchievementDetailByChallengeId(
    challengeId,
    language
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **challengeId** | [**string**] | Player uuid | defaults to undefined|
| **language** | [**string**] | local language | defaults to undefined|


### Return type

**AchievementDTO**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | Detailed achievement information |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **getChallengeToken**
> File getChallengeToken()


### Example

```typescript
import {
    AchievementApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new AchievementApi(configuration);

let challengeId: number; //Challenge ID (default to undefined)
let tier: Tier; //Optional tier (optional) (default to undefined)

const { status, data } = await apiInstance.getChallengeToken(
    challengeId,
    tier
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **challengeId** | [**number**] | Challenge ID | defaults to undefined|
| **tier** | **Tier** | Optional tier | (optional) defaults to undefined|


### Return type

**File**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: image/png


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | Challenge token image |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **getPlayerAchievementByPUUID**
> Array<PlayerAchievementDTO> getPlayerAchievementByPUUID()


### Example

```typescript
import {
    AchievementApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new AchievementApi(configuration);

let pUUID: string; //Player uuid (default to undefined)
let language: string; //local language (default to undefined)

const { status, data } = await apiInstance.getPlayerAchievementByPUUID(
    pUUID,
    language
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **pUUID** | [**string**] | Player uuid | defaults to undefined|
| **language** | [**string**] | local language | defaults to undefined|


### Return type

**Array<PlayerAchievementDTO>**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | Player achievement information |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

