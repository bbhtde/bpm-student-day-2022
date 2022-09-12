# Process Context Variables

The process contains the following process context variables:

| Variable Name| Typ | In-/External | Meaning                              | Note                    |
|:-------------|:----|:-------------|:-------------------------------------|:------------------------|
| email_address | String | External | Email address of course participant  | Input for process start |
| course_number | String | External | Course number used for registration  | Input for process start |
| iban          | String | Internal | IBAN of participant |  |
| pricing       | String | Internal | Type of pricing  | One of `FREE`, `PRICE_LEVEL_1`, `PRICE_LEVEL_2`, `PRICE_LEVEL_3`|
| prerequisites_met | Boolean | Internal | Are prerequisites for course reservation met? | `true` if prerequisites are met, else `false` |
| payment      | Double | Internal | Price to be paid for the course participation  |
