package org.zerock.springboot.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import org.zerock.springboot.common.entity.BaseEntity;
import org.zerock.springboot.member.entity.Member;

@Entity
@Builder
@Table(name = "orders")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "member")
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "login_id")
    private Member member;
}