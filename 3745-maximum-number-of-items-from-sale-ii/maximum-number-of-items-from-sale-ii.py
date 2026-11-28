class Solution:
    def maximumSaleItems(self, items: List[List[int]], budget: int) -> int:
        items.sort(key=lambda x: x[1])

        mx = max(factor for factor, price in items)

        freq = [0] * (mx + 1)

        for factor, price in items:
            freq[factor] += 1

        for i in range(1, mx + 1):
            for j in range(i * 2, mx + 1, i):
                freq[i] += freq[j]

        min_price = items[0][1]
        ans = 0

        for factor, price in items:
            if price > 2 * min_price:
                break

            count = freq[factor] - 1

            take = min(count, budget // price)

            ans += take * 2
            budget -= take * price

        ans += budget // min_price

        return ans